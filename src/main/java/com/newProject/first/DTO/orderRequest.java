package com.newProject.first.DTO;

import java.util.List;

public class orderRequest {
  private List<itemResponse> items;

    public orderRequest() {
    }

    public orderRequest(List<itemResponse> items) {
        this.items = items;
    }

    public List<itemResponse> getItems() {
        return items;
    }

    public void setItems(List<itemResponse> items) {
        this.items = items;
    }
}
