/*
 * Copyright 2017-present the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package info.yangguo.demo.atomix;

import com.google.common.collect.Lists;
import io.atomix.cluster.Member;
import io.atomix.cluster.Node;
import io.atomix.cluster.discovery.BootstrapDiscoveryProvider;
import io.atomix.core.Atomix;
import io.atomix.protocols.raft.partition.RaftPartitionGroup;
import io.atomix.storage.StorageLevel;

import java.io.File;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Copycat performance test.
 *
 * @author <a href="http://github.com/kuujo">Jordan Halterman</a>
 */
public class Server3 {
    public static void main(String[] args) {
        Member member1 = Member.builder().withId("gateway1").withAddress("127.0.0.1", 6001).build();
        Member member2 = Member.builder().withId("gateway2").withAddress("127.0.0.1", 6002).build();
        Member member3 = Member.builder().withId("gateway3").withAddress("127.0.0.1", 6003).build();
        List<Node> members = Lists.newArrayList();
        members.add(member1);
        members.add(member2);
        members.add(member3);


        Atomix atomix = Atomix.builder()
                .withMemberId(member3.id())
                .withAddress(member3.address())
                .withMembershipProvider(BootstrapDiscoveryProvider.builder()
                        .withNodes(members)
                        .build())
                .withManagementGroup(RaftPartitionGroup.builder("system")
                        .withMembers(members.stream().map(m -> m.id().id()).collect(Collectors.toSet()))
                        .withNumPartitions(1)
                        .withPartitionSize(members.size())
                        .withDataDirectory(new File(String.format("target/perf-logs/%s/system", member3.id())))
                        .build())
                .withPartitionGroups(RaftPartitionGroup.builder("data")
                        .withMembers(members.stream().map(m -> m.id().id()).collect(Collectors.toSet()))
                        .withNumPartitions(7)
                        .withPartitionSize(3)
                        .withStorageLevel(StorageLevel.DISK)
                        .withFlushOnCommit(false)
                        .withDataDirectory(new File(String.format("target/perf-logs/%s/data", member3.id())))
                        .build())
                .build();
        atomix.start().join();
        System.out.println("server3 started");
    }
}
