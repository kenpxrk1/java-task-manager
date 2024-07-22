package main.manager.history;

import main.tasks.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CustomLinkedList {
    private Node<Task> head;
    private Node<Task> tail;
    private int size = 0;

    public Map<Integer, Node<Task>> getNodeMap() {
        return nodeMap;
    }

    private Map<Integer, Node<Task>> nodeMap = new HashMap<>();

    private static class Node<T> {
        T task;
        Node<T> next;
        Node<T> prev;

        public Node(Node<T> prev, T task, Node<T> next) {
            this.prev = prev;
            this.task = task;
            this.next = next;
        }
    }

    public void linkLast(Task task) {
        final Node<Task> oldTail = (Node<Task>) tail;
        final Node<Task> newTail = new Node<>(oldTail, task, null);
        tail = newTail;
        if (oldTail == null) {
            head = newTail;
        } else {
            oldTail.next = newTail;
        }
        nodeMap.put(task.getId(), newTail);
    }

    public List<Task> getTasks() {
        List<Task> taskList = new ArrayList<>();
        if (head == null){
            return null;
        }
        Node<Task> currNode = tail;
        while (currNode != null) {
            taskList.add(currNode.task);
            currNode = currNode.prev;
        }
        return taskList;
    }

    private void removeNode(Node<Task> node) {
        if (node == null) return;

        if (node == head) {
            head = node.next;
            if (head != null) {
                head.prev = null;
            } else {
                tail = null;
            }
        } else if (node == tail) {
            tail = node.prev;
            if (tail != null) {
                tail.next = null;
            } else {
                head = null;
            }
        } else {
            node.prev.next = node.next;
            node.next.prev = node.prev;
        }
    }

    public void removeTask(Integer key) {
        removeNode(nodeMap.get(key));
        nodeMap.remove(key);
    }
}
