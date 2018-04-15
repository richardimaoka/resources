Kubernetes on Windows did not work on my PC - link the GitHub issue
So I tried Docker on Windows + Minikube on Windows

I also tried Docker on Windows + Minikube on Ubuntu (Windows Subsystems for Linux) but I could not get it work either

Minikube install
https://github.com/kubernetes/minikube/releases

Command Prompt with Admin previledge

>minikube start --vm-driver=hyper-v

minikube runs its own docker daemon
you need to push images to

C:\WINDOWS\system32>docker images
REPOSITORY                   TAG                 IMAGE ID            CREATED             SIZE
<none>                       <none>              32c6d72053e9        2 weeks ago         763MB
<none>                       <none>              0397695aecc7        2 weeks ago         763MB
openjdk                      latest              891c9734d5ab        3 weeks ago         726MB
cassandra                    latest              c6b513da2ff3        4 weeks ago         323MB

https://kubernetes.io/docs/getting-started-guides/minikube/
`eval $(minikube docker-env)`
Since no eval command on Windows Command Prompt

minikube docker-env
and it displays something like this:
C:\WINDOWS\system32>minikube docker-env
SET DOCKER_TLS_VERIFY=1
SET DOCKER_HOST=tcp://192.168.11.13:2376
SET DOCKER_CERT_PATH=C:\Users\nishyu\.minikube\certs
SET DOCKER_API_VERSION=1.23
REM Run this command to configure your shell:
REM @FOR /f "tokens=*" %i IN ('minikube docker-env') DO @%i

So do the last

@FOR /f "tokens=*" %i IN ('minikube docker-env') DO @%i

Then you do docker images

C:\WINDOWS\system32>docker images
REPOSITORY                                    TAG                 IMAGE ID            CREATED             SIZE
<none>                                        <none>              fc86229ecb10        2 weeks ago         7.61MB
<none>                                        <none>              0c847d881d20        2 weeks ago         7.61MB
k8s.gcr.io/kubernetes-dashboard-amd64         v1.8.1              e94d2f21bc0c        4 months ago        121MB
gcr.io/google-containers/kube-addon-manager   v6.5                d166ffa9201a        5 months ago        79.5MB
gcr.io/k8s-minikube/storage-provisioner       v1.8.1              4689081edb10        5 months ago        80.8MB
k8s.gcr.io/k8s-dns-sidecar-amd64              1.14.5              fed89e8b4248        6 months ago        41.8MB
k8s.gcr.io/k8s-dns-kube-dns-amd64             1.14.5              512cd7425a73        6 months ago        49.4MB
k8s.gcr.io/k8s-dns-dnsmasq-nanny-amd64        1.14.5              459944ce8cc4        6 months ago        41.4MB
gcr.io/google_containers/pause-amd64          3.0                 99e59f495ffa        23 months ago       747kB

Then build and publish your docker images to the Minikube docker daemon

cd *******
sbt
docker:publishLocal
docker images


[info] Packaging C:\Users\nishyu\richardimaoka\resources\akka-http-scoring\target\scala-2.12\akka-http-scoring_2.12-0.1-SNAPSHOT-sources.jar ...
[info] Done packaging.
[info] Wrote C:\Users\nishyu\richardimaoka\resources\akka-http-scoring\target\scala-2.12\akka-http-scoring_2.12-0.1-SNAPSHOT.pom
[info] Updating {file:/C:/Users/nishyu/richardimaoka/resources/akka-http-scoring/}root...
[info] Done updating.
[info] Main Scala API documentation to C:\Users\nishyu\richardimaoka\resources\akka-http-scoring\target\scala-2.12\api...
[info] Compiling 2 Scala sources to C:\Users\nishyu\richardimaoka\resources\akka-http-scoring\target\scala-2.12\classes ...
model contains 12 documentable templates
[info] Main Scala API documentation successful.
[info] Packaging C:\Users\nishyu\richardimaoka\resources\akka-http-scoring\target\scala-2.12\akka-http-scoring_2.12-0.1-SNAPSHOT-javadoc.jar ...
[info] Done packaging.
[info] Done compiling.
[info] Packaging C:\Users\nishyu\richardimaoka\resources\akka-http-scoring\target\scala-2.12\akka-http-scoring_2.12-0.1-SNAPSHOT.jar ...
[info] Done packaging.
[info] Sending build context to Docker daemon  36.79MB
[info] Step 1/6 : FROM openjdk:latest
[info]  ---> 891c9734d5ab
[info] Step 2/6 : WORKDIR /opt/docker
[info]  ---> Using cache
[info]  ---> b716b550cdff
[info] Step 3/6 : ADD --chown=daemon:daemon opt /opt
[info]  ---> e18942060608
[info] Step 4/6 : USER daemon
[info]  ---> Running in 9833b411e48a
[info]  ---> ed3025739877
[info] Removing intermediate container 9833b411e48a
[info] Step 5/6 : ENTRYPOINT bin/akka-http-scoring
[info]  ---> Running in c703dd3cd572
[info]  ---> f1c17c72a2ea
[info] Removing intermediate container c703dd3cd572
[info] Step 6/6 : CMD
[info]  ---> Running in 6922680bbdf1
[info]  ---> e69a601c9184
[info] Removing intermediate container 6922680bbdf1
[info] Successfully built e69a601c9184
[info] Successfully tagged akka-http-scoring:0.1-SNAPSHOT
[info] SECURITY WARNING: You are building a Docker image from Windows against a non-Windows Docker host. All files and directories added to build context will have '-rwxr-xr-x' permissions. It is recommended to double check and reset permissions for sensitive files and directories.
[info] Built image akka-http-scoring:0.1-SNAPSHOT
[success] Total time: 18 s, completed Apr 15, 2018 11:49:49 PM


C:\Users\nishyu\richardimaoka\resources\akka-http-scoring>docker images
REPOSITORY                                    TAG                 IMAGE ID            CREATED              SIZE
akka-http-scoring                             0.1-SNAPSHOT        e69a601c9184        About a minute ago   763MB
...
...

next, build and publish the wrk image
//don't forget the last .
> docker build --tag=wrk-akka-http-scoring:1.0-SNAPSHOT .

タグはlatest以外にする必要があることに注意。でないと、k8sはregistryからイメージをpullしてこようとしてしまいます。
(http://kubernetes.io/docs/getting-started-guides/minikube/#reusing-the-docker-daemon)


Step 1/5 : FROM williamyeh/wrk
 ---> 53a42fc6433d
Step 2/5 : ADD --chown=daemon:daemon scripts /data/scripts
 ---> Using cache
 ---> 02bf519dffb1
Step 3/5 : USER daemon
 ---> Using cache
 ---> a81c6be1dc68
Step 4/5 : ENTRYPOINT /usr/local/bin/wrk
 ---> Using cache
 ---> 255a578c2c95
Step 5/5 : CMD
 ---> Using cache
 ---> 19ad2be64a4b
Successfully built 19ad2be64a4b
Successfully tagged wrk-akka-http-scoring:1.0-SNAPSHOT
SECURITY WARNING: You are building a Docker image from Windows against a non-Windows Docker host. All files and directories added to build context will have '-rwxr-xr-x' permissions. It is recommended to double check and reset permissions for sensitive files and directories.


C:\Users\nishyu\richardimaoka\resources\akka-http-scoring\wrk>docker images
REPOSITORY                                    TAG                 IMAGE ID            CREATED             SIZE
akka-http-scoring                             0.1-SNAPSHOT        e69a601c9184        5 minutes ago       763MB
...
wrk-akka-http-scoring                         1.0-SNAPSHOT        19ad2be64a4b        2 weeks ago         7.61MB
...
...


Install Kubectl - how???

> kubectl apply -f deployment.yaml
deployment "akka-http-scoring" created

>kubectl get pods
NAME                                 READY     STATUS    RESTARTS   AGE
akka-http-scoring-5f9c4cd6c5-26z49   1/1       Running   0          40s

>kubectl apply -f service.yaml
service "akka-http-scoring" created

>kubectl get services
NAME                       TYPE        CLUSTER-IP      EXTERNAL-IP   PORT(S)   AGE
akka-http-scoring          ClusterIP   10.101.27.126   <none>        80/TCP    9s
kubernetes                 ClusterIP   10.96.0.1       <none>        443/TCP   14d

>kubectl apply -f pod.yaml
pod "wrk" created


> cd wrk
> kubectl logs wrk
Running 30s test @ http://10.101.27.126:80/scoring
  2 threads and 100 connections
  Thread Stats   Avg      Stdev     Max   +/- Stdev
    Latency    16.44ms  101.06ms   2.00s    98.58%
    Req/Sec    10.43k     6.01k   24.77k    58.82%
  620439 requests in 30.51s, 126.85MB read
  Socket errors: connect 0, read 0, write 0, timeout 49
Requests/sec:  20337.68
Transfer/sec:      4.16MB



C:\Users\nishyu\richardimaoka\resources\akka-http-scoring\wrk>kubectl delete pod wrk
pod "wrk" deleted

C:\Users\nishyu\richardimaoka\resources\akka-http-scoring\wrk>kubectl apply -f pod.yaml
pod "wrk" created

C:\Users\nishyu\richardimaoka\resources\akka-http-scoring\wrk>kubectl logs wrk
Running 30s test @ http://10.101.27.126:80/scoring
  2 threads and 100 connections
  Thread Stats   Avg      Stdev     Max   +/- Stdev
    Latency     5.51ms   22.25ms 621.34ms   97.84%
    Req/Sec    15.81k     4.12k   24.76k    71.10%
  952635 requests in 30.48s, 197.77MB read
Requests/sec:  31254.48
Transfer/sec:      6.49MB


-----------------------------

https://cloud.google.com/sdk/docs/quickstart-windows

gcloud container clusters get-credentials cluster-1 --zone asia-northeast1-a --project richard-scala-cassandra-sample

>kubectl config view
apiVersion: v1
clusters:
- cluster:
    certificate-authority-data: REDACTED
    server: https://35.200.71.72
  name: gke_richard-scala-cassandra-sample_asia-northeast1-a_cluster-1
- cluster:
    certificate-authority: C:\Users\nishyu\.minikube\ca.crt
    server: https://192.168.11.13:8443
  name: minikube
contexts:
- context:
    cluster: gke_richard-scala-cassandra-sample_asia-northeast1-a_cluster-1
    user: gke_richard-scala-cassandra-sample_asia-northeast1-a_cluster-1
  name: gke_richard-scala-cassandra-sample_asia-northeast1-a_cluster-1
- context:
    cluster: minikube
    user: minikube
  name: minikube
current-context: gke_richard-scala-cassandra-sample_asia-northeast1-a_cluster-1
kind: Config
preferences: {}
users:
- name: gke_richard-scala-cassandra-sample_asia-northeast1-a_cluster-1
  user:
    auth-provider:
      config:
        cmd-args: config config-helper --format=json
        cmd-path: C:\Users\nishyu\AppData\Local\Google\Cloud SDK\google-cloud-sdk\bin\gcloud.cmd
        expiry-key: '{.credential.token_expiry}'
        token-key: '{.credential.access_token}'
      name: gcp
- name: minikube
  user:
    client-certificate: C:\Users\nishyu\.minikube\client.crt
    client-key: C:\Users\nishyu\.minikube\client.key


docker tag  gcr.io/your-project-id/example-image

>docker tag akka-http-scoring:0.1-SNAPSHOT asia.gcr.io/richard-scala-cassandra-sample/akka-http-scoring:0.1-SNAPSHOT

C:\Users\nishyu\richardimaoka\resources\akka-http-scoring\wrk>docker images
REPOSITORY                                                     TAG                 IMAGE ID            CREATED             SIZE
asia.gcr.io/richard-scala-cassandra-sample/akka-http-scoring   0.1-SNAPSHOT        e69a601c9184        42 minutes ago      763MB
akka-http-scoring                                              0.1-SNAPSHOT        e69a601c9184        42 minutes ago      763MB


> gcloud docker -- push asia.gcr.io/richard-scala-cassandra-sample/akka-http-scoring:0.1-SNAPSHOT
WARNING: `gcloud docker` will not be supported for Docker client versions above 18.03. Please use `gcloud auth configure-docker` to configure `docker` to use `gcloud` as a credential helper, then use `docker` as you would for non-GCR registries, e.g. `docker pull gcr.io/project-id/my-image`. Add `--verbosity=error` to silence this warning, e.g. `gcloud docker --verbosity=error -- pull gcr.io/project-id/my-image`. See: https://cloud.google.com/container-registry/docs/support/deprecation-notices#gcloud-docker
The push refers to a repository [asia.gcr.io/richard-scala-cassandra-sample/akka-http-scoring]
1ec5b32bdf0f: Pushed
5d63bc19373b: Pushed
46da922b5615: Layer already exists
21087b7b28f7: Layer already exists
7e912d203101: Layer already exists
638babc3b650: Layer already exists
0ef6a87794b5: Layer already exists
20c527f217db: Layer already exists
61c06e07759a: Layer already exists
bcbe43405751: Layer already exists
e1df5dc88d2c: Layer already exists
0.1-SNAPSHOT: digest: sha256:b19f05c263dc5badd2f4c8f7d4f88d3b2c0c9463cb8f1bfe9dd49ac88641a69f size: 2630

>docker tag wrk-akka-http-scoring:1.0-SNAPSHOT asia.gcr.io/richard-scala-cassandra-sample/wrk-akka-http-scoring:1.0-SNAPSHOT

> gcloud docker -- push asia.gcr.io/richard-scala-cassandra-sample/wrk-akka-http-scoring:1.0-SNAPSHOT


>kubectl apply -f deployment-gcp.yaml
deployment "akka-http-scoring" created

C:\Users\nishyu\richardimaoka\resources\akka-http-scoring>kubectl get pods
NAME                                 READY     STATUS              RESTARTS   AGE
akka-http-scoring-58dd6f4f76-7sx2r   0/1       ContainerCreating   0          17s

>kubectl apply -f service.yaml

cd wrk
>kubectl apply -f pod-gcp.yaml


wrk	2018/04/16 0:54:05	Transfer/sec: 389.96KB
wrk	2018/04/16 0:54:05	Requests/sec: 1902.13
wrk	2018/04/16 0:54:05	Socket errors: connect 0, read 0, write 0, timeout 81
wrk	2018/04/16 0:54:05	57149 requests in 30.04s, 11.44MB read
wrk	2018/04/16 0:54:05	Req/Sec 0.99k 678.88 2.23k 50.95%
wrk	2018/04/16 0:54:05	Latency 49.74ms 79.00ms 1.98s 94.77%
wrk	2018/04/16 0:54:05	Thread Stats Avg Stdev Max +/- Stdev
wrk	2018/04/16 0:54:05	2 threads and 100 connections
wrk	2018/04/16 0:53:35	Running 30s test @ http://10.55.254.121:80/scoring

wrk	2018/04/16 0:55:54	Transfer/sec: 775.48KB
wrk	2018/04/16 0:55:54	Requests/sec: 3710.17
wrk	2018/04/16 0:55:54	Socket errors: connect 0, read 0, write 0, timeout 49
wrk	2018/04/16 0:55:54	111406 requests in 30.03s, 22.74MB read
wrk	2018/04/16 0:55:54	Req/Sec 1.87k 472.19 2.95k 68.90%
wrk	2018/04/16 0:55:54	Latency 34.30ms 90.09ms 1.88s 98.70%
wrk	2018/04/16 0:55:54	Thread Stats Avg Stdev Max +/- Stdev
wrk	2018/04/16 0:55:54	2 threads and 100 connections
wrk	2018/04/16 0:55:24	Running 30s test @ http://10.55.254.121:80/scoring


wrk	2018/04/16 0:57:14	Transfer/sec: 1.37MB
wrk	2018/04/16 0:57:14	Requests/sec: 6710.35
wrk	2018/04/16 0:57:14	Socket errors: connect 0, read 0, write 0, timeout 34
wrk	2018/04/16 0:57:14	201981 requests in 30.10s, 41.38MB read
wrk	2018/04/16 0:57:14	Req/Sec 3.40k 807.20 9.39k 74.50%
wrk	2018/04/16 0:57:14	Latency 26.23ms 105.94ms 1.99s 98.41%
wrk	2018/04/16 0:57:14	Thread Stats Avg Stdev Max +/- Stdev
wrk	2018/04/16 0:57:14	2 threads and 100 connections
wrk	2018/04/16 0:56:44	Running 30s test @ http://10.55.254.121:80/scoring

wrk	2018/04/16 0:59:15	Transfer/sec: 1.67MB
wrk	2018/04/16 0:59:15	Requests/sec: 8141.81
wrk	2018/04/16 0:59:15	Socket errors: connect 0, read 0, write 0, timeout 20
wrk	2018/04/16 0:59:15	244449 requests in 30.02s, 50.08MB read
wrk	2018/04/16 0:59:15	Req/Sec 4.15k 0.97k 8.78k 78.55%
wrk	2018/04/16 0:59:15	Latency 28.41ms 124.39ms 1.99s 97.89%
wrk	2018/04/16 0:59:15	Thread Stats Avg Stdev Max +/- Stdev
wrk	2018/04/16 0:59:15	2 threads and 100 connections
wrk	2018/04/16 0:58:45	Running 30s test @ http://10.55.254.121:80/scoring

wrk	2018/04/16 1:00:15	Transfer/sec: 1.69MB
wrk	2018/04/16 1:00:15	Requests/sec: 8250.12
wrk	2018/04/16 1:00:15	Socket errors: connect 0, read 0, write 0, timeout 5
wrk	2018/04/16 1:00:15	247672 requests in 30.02s, 50.74MB read
wrk	2018/04/16 1:00:15	Req/Sec 4.21k 811.28 8.26k 82.91%
wrk	2018/04/16 1:00:15	Latency 27.95ms 120.75ms 1.97s 97.85%
wrk	2018/04/16 1:00:15	Thread Stats Avg Stdev Max +/- Stdev
wrk	2018/04/16 1:00:15	2 threads and 100 connections
wrk	2018/04/16 0:59:45	Running 30s test @ http://10.55.254.121:80/scoring

wrk	2018/04/16 1:01:19	Transfer/sec: 1.71MB
wrk	2018/04/16 1:01:19	Requests/sec: 8288.68
wrk	2018/04/16 1:01:19	Socket errors: connect 0, read 0, write 0, timeout 6
wrk	2018/04/16 1:01:19	248799 requests in 30.02s, 51.27MB read
wrk	2018/04/16 1:01:19	Req/Sec 4.20k 0.88k 8.91k 81.34%
wrk	2018/04/16 1:01:19	Latency 28.36ms 124.65ms 1.98s 97.86%
wrk	2018/04/16 1:01:19	Thread Stats Avg Stdev Max +/- Stdev
wrk	2018/04/16 1:01:19	2 threads and 100 connections
wrk	2018/04/16 1:00:48	Running 30s test @ http://10.55.254.121:80/scoring

> kubectl apply -f deployment-gcp.yaml //replica = 5

wrk	2018/04/16 1:04:11	Transfer/sec: 5.82MB
wrk	2018/04/16 1:04:11	Requests/sec: 28530.59
wrk	2018/04/16 1:04:11	Socket errors: connect 0, read 0, write 0, timeout 7
wrk	2018/04/16 1:04:11	857689 requests in 30.06s, 174.86MB read
wrk	2018/04/16 1:04:11	Req/Sec 14.45k 4.68k 25.88k 82.18%
wrk	2018/04/16 1:04:11	Latency 13.55ms 74.87ms 1.90s 98.56%
wrk	2018/04/16 1:04:11	Thread Stats Avg Stdev Max +/- Stdev
wrk	2018/04/16 1:04:11	2 threads and 100 connections
wrk	2018/04/16 1:03:41	Running 30s test @ http://10.55.254.121:80/scoring

wrk	2018/04/16 1:06:18	Transfer/sec: 7.71MB
wrk	2018/04/16 1:06:18	Requests/sec: 37574.30
wrk	2018/04/16 1:06:18	1130195 requests in 30.08s, 232.02MB read
wrk	2018/04/16 1:06:18	Req/Sec 18.91k 2.83k 25.05k 68.34%
wrk	2018/04/16 1:06:18	Latency 2.52ms 7.56ms 311.25ms 99.27%
wrk	2018/04/16 1:06:18	Thread Stats Avg Stdev Max +/- Stdev
wrk	2018/04/16 1:06:18	2 threads and 100 connections
wrk	2018/04/16 1:05:48	Running 30s test @ http://10.55.254.121:80/scoring

wrk	2018/04/16 1:07:37	Transfer/sec: 8.11MB
wrk	2018/04/16 1:07:37	Requests/sec: 39504.58
wrk	2018/04/16 1:07:37	1188490 requests in 30.08s, 244.12MB read
wrk	2018/04/16 1:07:37	Req/Sec 19.87k 1.81k 23.64k 81.61%
wrk	2018/04/16 1:07:37	Latency 2.56ms 9.50ms 516.44ms 99.41%
wrk	2018/04/16 1:07:37	Thread Stats Avg Stdev Max +/- Stdev
wrk	2018/04/16 1:07:37	2 threads and 100 connections
wrk	2018/04/16 1:07:07	Running 30s test @ http://10.55.254.121:80/scoring

wrk	2018/04/16 1:08:49	Transfer/sec: 5.34MB
wrk	2018/04/16 1:08:49	Requests/sec: 25968.39
wrk	2018/04/16 1:08:49	780965 requests in 30.07s, 160.61MB read
wrk	2018/04/16 1:08:49	Req/Sec 13.08k 3.57k 22.43k 72.94%
wrk	2018/04/16 1:08:49	Latency 6.07ms 45.73ms 1.39s 99.33%
wrk	2018/04/16 1:08:49	Thread Stats Avg Stdev Max +/- Stdev
wrk	2018/04/16 1:08:49	2 threads and 100 connections
wrk	2018/04/16 1:08:19	Running 30s test @ http://10.55.254.121:80/scoring

wrk	2018/04/16 1:12:37	Transfer/sec: 4.63MB
wrk	2018/04/16 1:12:37	Requests/sec: 22463.11
wrk	2018/04/16 1:12:37	675474 requests in 30.07s, 139.18MB read
wrk	2018/04/16 1:12:37	Req/Sec 11.30k 1.59k 15.01k 80.78%
wrk	2018/04/16 1:12:37	Latency 3.79ms 9.77ms 517.70ms 99.28%
wrk	2018/04/16 1:12:37	Thread Stats Avg Stdev Max +/- Stdev
wrk	2018/04/16 1:12:37	2 threads and 100 connections
wrk	2018/04/16 1:12:07	Running 30s test @ http://10.55.254.121:80/scoring