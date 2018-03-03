from flask import Flask
from os import environ
from random import randint

app = Flask(__name__)


@app.route("/")
def main():
    var = environ.get('FOO')
    res = str(randint(0, 100))
    return "Welcome! The random number is {} and the value of the environment variable 'FOO' is {}\n".format(res, var)


if __name__ == "__main__":
    app.run(host='0.0.0.0')
