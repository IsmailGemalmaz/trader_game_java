package com.example.myapplication.controller;

public interface EventListener {
    void onEventReceive(int event, Object... data);
}
