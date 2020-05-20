import requests
import smtplib
import mail_data

from email.mime.multipart import MIMEMultipart
from email.mime.text import MIMEText


def template(trip):
    tem = open('template.html', 'r')
    tem = tem.readlines()
    html = "".join(tem)
    print()
    html = html.replace("$TITLE$", trip['name'])
    html = html.replace("$IMAGE$", trip['imageLink'])
    html = html.replace("$LINK$", trip['link'])
    html = html.replace("$PRICE$", str(trip['price']))
    html = html.replace("$START$", trip['startDate'][:10])
    html = html.replace("$END$", trip['endDate'][:10])
    html = html.replace("$DESCRIPTION$", trip['description'][:50] + "...")
    return html


header = '''<!DOCTYPE html><html lang="en"><head>  <meta charset="UTF-8">
    <title>Title</title>
    <style>
        .post {
            min-width: 300px;
            width: 300px;
            box-shadow: 0 0 10px 10px rgba(0,0,0,0.1);
            border-radius: 4px;
            min-height: 500px;
            padding: 10px;

        }
        .image > img{
            width: 300px;
            height: 200px;
            object-fit: cover;
        }
        .more {
            width: 100%;
            margin: 10px auto;
            padding: 10px;
        }

    </style>
</head>
<body>'''

end = '''
</body>
</html>
'''

# python3 mail.py
url = 'https://tash.wtf/api/recommendations/undelivered'
data = requests.get(url).json()
mails = {}
for trip in data:
    mail = trip['criteria']['notifactionEmail']
    arr = mails.get(mail, [])
    arr.append(template(trip['trip']))
    mails[mail] = arr
try:
    server = smtplib.SMTP(mail_data.SMTP, 587)
    server.ehlo()
    server.starttls()
    server.login(mail_data.USERNAME, mail_data.PASSWORD)
    me = mail_data.USERNAME
    for mail in mails:
        msg = MIMEMultipart('alternative')
        msg['Subject'] = "Travel Agency"
        msg["From"] = me
        msg["To"] = mail
        string = header
        string += "".join(mails[mail])
        string += end
        msg.attach(MIMEText(string, 'html'))
        server.sendmail(me, mail, msg.as_string())

except:
    print('Something went wrong...')

for i in data:
    requests.delete('https://tash.wtf/api/recommendations/' + str(i['id']) + '/undelivered')
    print(str(i['id']) + ' recommendation sent')

if len(data) > 0:
    print('sent')