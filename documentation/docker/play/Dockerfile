FROM openjdk:8-jdk
MAINTAINER Anthony PERIQUET <anthony@periquet.net>

# Upgrade environment
RUN apt-get update && apt-get upgrade -y && \
    # Get play sources
    wget https://downloads.typesafe.com/play/1.4.4/play-1.4.4.zip && \
    # Create required directories
    mkdir -p /opt/play /app && \
    # Extract play
    unzip play-1.4.4.zip -d /opt/play/

# Clean archive
RUN rm play-1.4.4.zip

# Define volume
VOLUME /app

# Expose port 9000 (HTTP)
EXPOSE 9000
# Expose port 8000 (debug)
# EXPOSE 8000

ENTRYPOINT ["/opt/play/play-1.4.4/play"]

CMD ["run", "/app"]