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