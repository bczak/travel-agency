import psycopg2
import config


class DB:
    def __init__(self):
        print("Connecting...")
        self.conn = psycopg2.connect(host=config.database['host'], database=config.database['database'],
                                     user=config.database['user'], password=config.database['password'])
        print("Connected")
        self.cur = self.conn.cursor()

    def select(self, sql):
        self.cur.execute(sql)
        result = self.cur.fetchall()
        return result

    def insert(self, sql: str, data: tuple) -> object:
        self.cur.execute(sql, data)
        self.conn.commit()
        return None
