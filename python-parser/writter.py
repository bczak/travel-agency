from db import DB
import process
import os
from datetime import datetime, timedelta


con = DB()
i = 0
id = 100
for item in os.listdir('json'):
    if i > 0:
        break
    i = i + 1
    data = process.tagging(item[:-5])
    dates = [(k, v) for k, v in data[1]['dates'].items()]
    end = data[1]['days']
    date = datetime(int(dates[0][0][:4]), int(dates[0][0][5:7]), int(dates[0][0][8:]))
    date = date + timedelta(int(end))
    trip = {
        'name': data[1]['title'],
        'description': data[1]['description'],
        "price": float(dates[0][1][4:]),
        "start_date": int(datetime.timestamp(datetime.strptime(dates[0][0], "%Y-%m-%d"))),
        "end_date": int(datetime.timestamp(date)),
        "link": data[1]['link'],
        "id": id,
        "length": int(data[1]['days']),
        "location": "World"
    }
    id = id + 1
    print(trip)
    con.insert("insert into \"trip_table\" (id, end_date, length, location, name, price, start_date, description, link) "
               "values (%s, to_timestamp(%s), %s, %s, %s, %s, to_timestamp(%s), %s, %s);",
               (id, trip['end_date'], trip['length'], trip['location'], trip['name'], trip['price'], trip['start_date'], trip['description'], trip['link']))


# process.tagging()
