from python:2.7

RUN apt-get update
RUN pip install --upgrade pip
RUN pip install flask
EXPOSE 5000
COPY app.py /usr/local/etc/app.py
CMD python /usr/local/etc/app.py