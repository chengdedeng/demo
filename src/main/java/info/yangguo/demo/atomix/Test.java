package info.yangguo.demo.atomix;

import io.atomix.cluster.Node;
import io.atomix.core.Atomix;
import io.atomix.messaging.Endpoint;
import io.atomix.utils.serializer.KryoNamespace;
import io.atomix.utils.serializer.KryoNamespaces;
import io.atomix.utils.serializer.Serializer;

import java.io.File;
import java.util.concurrent.CompletableFuture;


public class Test {
    private static final Serializer SERIALIZER = Serializer.using(KryoNamespaces.BASIC);

    public void server1() throws InterruptedException {
        Atomix.Builder builder = Atomix.builder();
        builder.withLocalNode(Node.builder("server1")
                .withType(Node.Type.DATA)
                .withEndpoint(Endpoint.from("127.0.0.1", 5001))
                .build());
        builder.withBootstrapNodes(
                Node.builder("server1")
                        .withType(Node.Type.DATA)
                        .withEndpoint(Endpoint.from("127.0.0.1", 5001))
                        .build(),
                Node.builder("server2")
                        .withType(Node.Type.DATA)
                        .withEndpoint(Endpoint.from("127.0.0.1", 5002))
                        .build(),
                Node.builder("server3")
                        .withType(Node.Type.DATA)
                        .withEndpoint(Endpoint.from("127.0.0.1", 5003))
                        .build());
        Atomix atomix = builder.withDataDirectory(new File(System.getProperty("user.dir"), "data/server1")).build();

        atomix.messagingService().subscribe("test", message -> {
            System.out.println("server1 message:" + message.toString());
            return CompletableFuture.completedFuture(message);
        });
        atomix.<String>eventingService().subscribe("event", SERIALIZER::decode, message -> {
            System.out.println("server1 event:" + message);
            return CompletableFuture.completedFuture(message);
        }, SERIALIZER::encode);

        atomix.start();
    }

    public void server2() throws InterruptedException {
        Atomix.Builder builder = Atomix.builder();
        builder.withLocalNode(Node.builder("server2")
                .withType(Node.Type.DATA)
                .withEndpoint(Endpoint.from("127.0.0.1", 5002))
                .build());
        builder.withBootstrapNodes(
                Node.builder("server1")
                        .withType(Node.Type.DATA)
                        .withEndpoint(Endpoint.from("127.0.0.1", 5001))
                        .build(),
                Node.builder("server2")
                        .withType(Node.Type.DATA)
                        .withEndpoint(Endpoint.from("127.0.0.1", 5002))
                        .build(),
                Node.builder("server3")
                        .withType(Node.Type.DATA)
                        .withEndpoint(Endpoint.from("127.0.0.1", 5003))
                        .build());
        Atomix atomix = builder.withDataDirectory(new File(System.getProperty("user.dir"), "data/server2")).build();

        atomix.messagingService().subscribe("test", message -> {
            System.out.println("server2 message:" + message.toString());
            return CompletableFuture.completedFuture(message);
        });
        atomix.<String>eventingService().subscribe("event", SERIALIZER::decode, message -> {
            System.out.println("server2 event:" + message);
            return CompletableFuture.completedFuture(message);
        }, SERIALIZER::encode);
        atomix.start();
    }

    public void server3() throws InterruptedException {
        Atomix.Builder builder = Atomix.builder();
        builder.withLocalNode(Node.builder("server3")
                .withType(Node.Type.DATA)
                .withEndpoint(Endpoint.from("127.0.0.1", 5003))
                .build());
        builder.withBootstrapNodes(
                Node.builder("server1")
                        .withType(Node.Type.DATA)
                        .withEndpoint(Endpoint.from("127.0.0.1", 5001))
                        .build(),
                Node.builder("server2")
                        .withType(Node.Type.DATA)
                        .withEndpoint(Endpoint.from("127.0.0.1", 5002))
                        .build(),
                Node.builder("server3")
                        .withType(Node.Type.DATA)
                        .withEndpoint(Endpoint.from("127.0.0.1", 5003))
                        .build());
        Atomix atomix = builder.withDataDirectory(new File(System.getProperty("user.dir"), "data/server3")).build();

        atomix.messagingService().subscribe("test", message -> {
            System.out.println("server3 message:" + message.toString());
            return CompletableFuture.completedFuture(message);
        });
        atomix.<String>eventingService().subscribe("event", SERIALIZER::decode, message -> {
            System.out.println("server3 event:" + message);
            return CompletableFuture.completedFuture(message);
        }, SERIALIZER::encode);

        atomix.start();
    }

    @org.junit.Test
    public void startCluster() throws InterruptedException {
        server1();
        server2();
        server3();

        Thread.sleep(1000000000L);
    }

    @org.junit.Test
    public void client() throws Exception {
        Atomix atomix = Atomix.builder()
                .withLocalNode(Node.builder("client")
                        .withType(Node.Type.CLIENT)
                        .withEndpoint(Endpoint.from("127.0.0.1", 5004))
                        .build())
                .withBootstrapNodes(
                        Node.builder("server1")
                                .withType(Node.Type.DATA)
                                .withEndpoint(Endpoint.from("127.0.0.1", 5001))
                                .build(),
                        Node.builder("server2")
                                .withType(Node.Type.DATA)
                                .withEndpoint(Endpoint.from("127.0.0.1", 5002))
                                .build(),
                        Node.builder("server3")
                                .withType(Node.Type.DATA)
                                .withEndpoint(Endpoint.from("127.0.0.1", 5003))
                                .build())
                .build();

        atomix.start().join();

//        LeaderElector leaderElector=atomix.leaderElectorBuilder("test").build();
//        AsyncLeaderElector<NodeId> asyncElector = leaderElector.async();
//        asyncElector.run("foo", atomix.clusterService().getLocalNode().id()).thenAccept(leadership -> {
//            if (leadership.leader().id().equals(atomix.clusterService().getLocalNode().id())) {
//                // Local node elected leader!
//            }
//        });

        Serializer dataSerializer = Serializer.using(KryoNamespace.builder()
                .register(KryoNamespaces.BASIC)
                .build());

//        ConsistentMap<String,Map> map = atomix.<String,Map>consistentMapBuilder("test").withSerializer(dataSerializer).withPersistence(Persistence.PERSISTENT).build();
//        Map<String, Object> fileInfo = new HashMap<>();
//        fileInfo.put("name", "QQ.dmg");
//        fileInfo.put("data", IOUtils.toByteArray(FileUtils.openInputStream(new File("/Users/guo/Downloads/QQ.dmg"))));
//        map.put("12345", fileInfo);

//        List<Subscription> list = atomix.eventingService().getSubscriptions("event");
//        atomix.eventingService().subscribe("event", message -> {
//            return CompletableFuture.completedFuture(message);
//        });
//        atomix.messagingService().broadcast("test", "Hello world!");
//        atomix.messagingService().broadcast("event1", "Hello world!");
//        atomix.<String>eventingService().send("event", "Hello world!").thenAccept(response -> {
//            System.out.println("Received " + response);
//        });
//        atomix.<String>eventingService().broadcast("event", "Hello world!");

//        File file= (File) map.get("file").value();
//        System.out.println(map.get("12345").value().get("name"));
        System.out.println("----");
    }

}
