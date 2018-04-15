FROM williamyeh/wrk
ADD --chown=daemon:daemon scripts /data/scripts
USER daemon
ENTRYPOINT ["/usr/local/bin/wrk"]
CMD []