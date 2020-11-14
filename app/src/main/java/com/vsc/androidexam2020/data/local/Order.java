package com.vsc.androidexam2020.data.local;

import com.vsc.androidexam2020.data.remote.api.OrderResponse;

import java.util.ArrayList;
import java.util.List;

public class Order implements Comparable<Order> {

    private String fileName;
    private String fileSize;
    private String price;
    private long createdOn;
    private long completionTime;

    public String getFileName() {
        return fileName;
    }

    public String getFileSize() {
        return fileSize;
    }

    public String getPrice() {
        return price;
    }

    public long getCreatedOn() {
        return createdOn;
    }

    public long getCompletionTime() {
        return completionTime;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public void setFileSize(String fileSize) {
        this.fileSize = fileSize;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setCreatedOn(long createdOn) {
        this.createdOn = createdOn;
    }

    public void setCompletionTime(long completionTime) {
        this.completionTime = completionTime;
    }

    public static Order fromOrderResponse(OrderResponse orderResponse) {
        Order order = new Order();
        order.setFileName(orderResponse.getFilename());
        order.setFileSize(orderResponse.getFilesize());
        order.setPrice(orderResponse.getPrize());
        order.setCreatedOn(orderResponse.getCreatedOn());
        order.setCompletionTime(orderResponse.getCompletionTime());
        return order;
    }

    public static List<Order> listFromOrderResponses(List<OrderResponse> orderResponses) {
        List<Order> orders = new ArrayList<>();
        for (OrderResponse orderResponse : orderResponses) {
            orders.add(Order.fromOrderResponse(orderResponse));
        }
        return orders;
    }

    @Override
    public String toString() {
        return "Order{" +
                "fileName='" + fileName + '\'' +
                ", fileSize='" + fileSize + '\'' +
                ", price='" + price + '\'' +
                ", createdOn=" + createdOn +
                ", completionTime=" + completionTime +
                '}';
    }

    @Override
    public int compareTo(Order o) {
        return createdOn + completionTime < o.createdOn + o.completionTime ? 1 : -1;
    }
}
