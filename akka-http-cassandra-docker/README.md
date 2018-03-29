Blog article is at https://richardimaoka.github.io/blog/benchmarking-akka-http-cassandra-local/

## Raw result of performance test:

## JSON marshalling and unmarshalling only

```
$ wrk -t2 -c100 -d30s  -s wrk-script.lua http://localhost:8095/scoring
Running 30s test @ http://localhost:8095/scoring
  2 threads and 100 connections
  Thread Stats   Avg      Stdev     Max   +/- Stdev
    Latency    11.11ms   76.63ms   1.31s    98.07%
    Req/Sec    23.18k     5.52k   34.43k    84.63%
  1368767 requests in 30.04s, 281.16MB read
Requests/sec:  45571.24
Transfer/sec:      9.36MB

$ wrk -t2 -c100 -d30s  -s wrk-script.lua http://localhost:8095/scoring
Running 30s test @ http://localhost:8095/scoring
  2 threads and 100 connections
  Thread Stats   Avg      Stdev     Max   +/- Stdev
    Latency     2.17ms    9.29ms 321.87ms   99.21%
    Req/Sec    26.26k     3.70k   34.27k    67.45%
  1568938 requests in 30.06s, 325.72MB read
Requests/sec:  52196.74
Transfer/sec:     10.84MB

$ wrk -t2 -c100 -d30s  -s wrk-script.lua http://localhost:8095/scoring
Running 30s test @ http://localhost:8095/scoring
  2 threads and 100 connections
  Thread Stats   Avg      Stdev     Max   +/- Stdev
    Latency     2.16ms    6.74ms 267.60ms   99.02%
    Req/Sec    24.29k     4.00k   32.15k    78.09%
  1450092 requests in 30.03s, 301.05MB read
Requests/sec:  48293.76
Transfer/sec:     10.03MB
```

## In-memory persistence

```
$ wrk -t2 -c100 -d30s  -s wrk-script.lua http://localhost:8095/scoring
Running 30s test @ http://localhost:8095/scoring
  2 threads and 100 connections
  Thread Stats   Avg      Stdev     Max   +/- Stdev
    Latency     4.32ms   20.91ms 436.33ms   98.18%
    Req/Sec    13.87k     3.64k   25.62k    86.22%
  819745 requests in 30.02s, 157.71MB read
Requests/sec:  27302.85
Transfer/sec:      5.25MB

$ wrk -t2 -c100 -d30s  -s wrk-script.lua http://localhost:8095/scoring
Running 30s test @ http://localhost:8095/scoring
  2 threads and 100 connections
  Thread Stats   Avg      Stdev     Max   +/- Stdev
    Latency     2.41ms    4.68ms 152.84ms   97.57%
    Req/Sec    13.88k     2.76k   23.15k    81.11%
  826514 requests in 30.05s, 161.00MB read
Requests/sec:  27507.16
Transfer/sec:      5.36MB

$ wrk -t2 -c100 -d30s  -s wrk-script.lua http://localhost:8095/scoring
Running 30s test @ http://localhost:8095/scoring
  2 threads and 100 connections
  Thread Stats   Avg      Stdev     Max   +/- Stdev
    Latency     2.10ms    3.30ms 119.31ms   97.77%
    Req/Sec    15.49k     2.15k   25.64k    82.69%
  924944 requests in 30.08s, 180.73MB read
Requests/sec:  30746.86
Transfer/sec:      6.01MB
```

### Cassandra

```
$ wrk -t2 -c100 -d30s  -s wrk-script.lua http://localhost:8095/scoring
Running 30s test @ http://localhost:8095/scoring
  2 threads and 100 connections
  Thread Stats   Avg      Stdev     Max   +/- Stdev
    Latency   118.36ms   63.42ms 837.41ms   92.26%
    Req/Sec   449.09    132.62   666.00     66.95%
  26440 requests in 30.07s, 5.02MB read
Requests/sec:    879.31
Transfer/sec:    171.02KB

$ wrk -t2 -c100 -d30s  -s wrk-script.lua http://localhost:8095/scoring
Running 30s test @ http://localhost:8095/scoring
  2 threads and 100 connections
  Thread Stats   Avg      Stdev     Max   +/- Stdev
    Latency   101.55ms   30.35ms 335.73ms   91.36%
    Req/Sec   498.90    113.38   676.00     78.67%
  29873 requests in 30.09s, 5.70MB read
Requests/sec:    992.86
Transfer/sec:    193.92KB

$ wrk -t2 -c100 -d30s  -s wrk-script.lua http://localhost:8095/scoring
Running 30s test @ http://localhost:8095/scoring
  2 threads and 100 connections
  Thread Stats   Avg      Stdev     Max   +/- Stdev
    Latency    88.92ms    9.46ms 199.44ms   79.70%
    Req/Sec   564.04     60.49   710.00     76.17%
  33759 requests in 30.08s, 6.44MB read
Requests/sec:   1122.35
Transfer/sec:    219.21KB
```

## Caveats

Well, I found something weird with my experiment ... maybe something in my code was wrong.

For the case 1, JSON marshalling/unmarshalling only, the total number of requests was:

```
1,368,767 (1st attempt)
+ 1,568,938 (2nd attempt)
+ 1,450,092 (3rd attempt)
= 4,387,797
```

However, when I checked the internal state of the server (`var`s), the number of requests was like 3000 fewer than that (I lost the exact number..). Well if HTTP requests were lost in the middle, that explains, but I didn't see anything from the wrk output or web server log...

Even more weird was the case 3. The total number of requests was:

```
26,440 (1st attempt)
+ 29,873 (2nd attempt)
+ 33,759 (3rd attempt)
= 90,072
```

but Cassandra had:

```
cqlsh> select count(*) from akka.messages where persistence_id = 'scoring' and partition_nr = 0;

 count
-------
 90370
```

How come Cassandra can have more rows than what was requested ?????????!?!?!?!?