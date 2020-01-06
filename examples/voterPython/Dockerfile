# Build upon this image "alpine" is a lightweight distro
FROM python:3.7-alpine

# Copy everthing from . to /app inside the 'box'
COPY . /app
WORKDIR /app

# Install all the requirements
RUN pip install -r requirements.txt

# How to run it when we start up the box?
RUN chmod +x run_basic.sh
CMD ["./run_basic.sh"]
