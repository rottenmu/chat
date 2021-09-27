package com.open.im.broker.cluster;

import java.io.Serializable;
import java.util.Objects;

/**
 * <p>Title: OpenIm</p>
 * <p>Description: OpenIm</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 *
 * @author rotten
 * @version 1.0
 */

public class Instance implements Comparable<Instance>, Serializable {
        private static final long serialVersionUID = -499010884211304846L;
        private long id;
        private int balance;
        private String host;
        private int port;

        public Instance(){}

        public Instance(String host, int port) {
            this.host = host;
            this.port = port;
        }

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public int getBalance() {
            return balance;
        }

        public void setBalance(int balance) {
            this.balance = balance;
        }

        public String getHost() {
            return host;
        }

        public void setHost(String host) {
            this.host = host;
        }

        public int getPort() {
            return port;
        }

        public void setPort(int port) {
            this.port = port;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            Instance instance = (Instance) o;
//        return id == node.id &&
            return Objects.equals(host, instance.host) && Objects.equals(port, instance.port);
        }

        @Override
        public int hashCode() {
            return Objects.hash(id, host, port);
        }

        @Override
        public int compareTo(Instance instance) {
            int weight1 = this.balance;
            int weight2 = instance.balance;
            if (weight1 > weight2)
            {
                return 1;
            } else if (weight1 < weight2)
            {
                return -1;
            }
            return 0;
        }

        public void incrementBalance()
        {
            balance++;
        }

        public void decrementBalance()
        {
            balance--;
        }

        @Override
        public String toString() {
            return "Node{" +
                    "id=" + id +
                    ", balance=" + balance +
                    ", host='" + host + '\'' +
                    ", port=" + port +
                    '}';
        }
    }