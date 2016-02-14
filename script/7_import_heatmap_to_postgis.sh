#
# install Postgres + PostGIS
#
# https://gist.github.com/juniorz/1081907
#

#
# 1. re-create Database timi_tz
#
dropdb phumblr
createdb -T postgis_template -O postgres phumblr

#
# 2. import Shapefile
#
shp2pgsql -s 25833 -W "latin1" data/flickr.shp public.flickr | psql -d $DB -U $USER_NAME;
psql -h localhost -d phumblr -U postgres -f data/shp2pgsql/flickr.sql
shp2pgsql -s 25833 -W "latin1" data/wiki_de.shp public.wiki > data/shp2pgsql/wiki.sql
psql -h localhost -d phumblr -U postgres -f data/shp2pgsql/wiki.sql
shp2pgsql -d -s 25833 -W "latin1" data/flickr_heat_gt10_500.shp public.flickr_heatmap > data/shp2pgsql/flickr_heat_gt10_500.sql
psql -h localhost -d phumblr -U postgres -f data/shp2pgsql/flickr_heat_gt10_500.sql

#Filter Wikipedia: "title" NOT LIKE 'Liste %' and "type" not in ('airport', 'country', 'landmarklist', 'without')

#update wiki set geom = ST_Transform(
#        ST_SetSRID(ST_MakePoint(lon, lat),4326),
#        25833)

#CREATE INDEX flickr_gix ON flickr USING GIST (geom);
#CREATE INDEX flickr_heatmap_gix ON flickr_heatmap USING GIST (geom);
#CREATE INDEX wiki_gix ON wiki USING GIST (geom);

CREATE INDEX ON flickr (owner);
CREATE INDEX ON flickr (views);
CREATE INDEX ON hotspot_cache (max_views);
CREATE INDEX ON hotspot_cache (owner);
CREATE INDEX ON hotspot_cache (hotspot_id);


CREATE INDEX flickr_gix ON flickr USING GIST (geom);
CREATE INDEX flickr_heatmap_gix ON flickr_heatmap USING GIST (geom);
CREATE INDEX wiki_gix ON wiki USING GIST (geom);

drop table hotspot_cache;
create table hotspot_cache as
select distinct
	h.gid as hotspot_id,
	f.owner as owner,
	max(f.views) max_views
from flickr f, flickr_heatmap h
where ST_Contains(h.geom, f.geom)
group by hotspot_id, owner


-- create new chace for photoid
drop table hotspot_photo_cache;
create table hotspot_photo_cache as
select h.gid as hotspot_id,
photo_id,
f.views
from flickr f, flickr_heatmap h
where ST_Contains(h.geom, f.geom);

CREATE INDEX hotspot_photo_id_idx
ON hotspot_photo_cache
USING btree
(hotspot_id);

CREATE INDEX hotspot_photo_pid_idx
ON hotspot_photo_cache
USING btree
(photo_id COLLATE pg_catalog."default");

CREATE INDEX hotspot_photo_views_idx
ON hotspot_photo_cache
USING btree
(views);



select f.owner, f.views, f.photo_id
from flickr f, hotspot_cache c
where 	c.hotspot_id = 45
	and f.owner = c.owner
	and f.views = c.max_views
order by f.views desc


select f.owner, f.views, f.photo_id
from flickr f, hotspot_cache c
where 	c.hotspot_id = 45
	and f.owner = c.owner
	and f.views = c.max_views
order by f.views desc
limit 5