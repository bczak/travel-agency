from db import DB
import process
import os
from datetime import datetime, timedelta

con = DB()
i = 0
id = 4000
for item in os.listdir('json'):
    # if i < 5:
    #     i = i + 1
    #     continue
    data = process.tagging(item[:-5])
    json = data[1]
    dates = [(k, v) for k, v in data[1]['dates'].items()]
    end = data[1]['days']
    date = datetime(int(dates[0][0][:4]), int(dates[0][0][5:7]), int(dates[0][0][8:]))
    date = date + timedelta(int(end))
    trip = {
        'name': data[1]['title'],
        'description': data[1]['description'][:250] + '...',
        "price": float(dates[0][1][4:]),
        "start_date": int(datetime.timestamp(datetime.strptime(dates[0][0], "%Y-%m-%d"))),
        "end_date": int(datetime.timestamp(date)),
        "link": data[1]['link'],
        "id": id,
        "length": int(data[1]['days']),
        "location": json['countries'],
        "image": json['image']
    }
    id = id + 1
    con.insert(
        "insert into \"trip_table\" (id, end_date, length, location, name, price, start_date, description, link, image_link) "
        "values (%s, to_timestamp(%s), %s, %s, %s, %s, to_timestamp(%s), %s, %s, %s);",
        (id, trip['end_date'], trip['length'], trip['location'], trip['name'], trip['price'], trip['start_date'],
         trip['description'][:250] + '...', trip['link'], trip['image']))
    tags = list(data[0].keys())
    tags.reverse()
    tags = tags[:-10]
    count = 0
    for tag in tags:
        if count == 6: continue
        t = con.select("select id from \"tag_table\" where name = '" + tag + "'")
        if len(t) == 0: continue
        con.insert("insert  into \"trip_table_tags\" values (%s, %s)", (id, t[0][0]))
        count  += 1

    for ctr in str(trip['location']).split(', '):
        t = con.select("select id from \"country_table\" where name = '" + ctr + "'")
        if len(t) == 0:
            continue
        # con.insert("insert  into \"country_table_trip\" values (%s, %s)", (t[0][0], id))
        con.insert("insert  into \"country_trip\" values (%s, %s)", (t[0][0], id))

    print(trip['name'] + ' added')
