package com.kafka.dto;

public class User {
    private String name;
    private int age;
    private String favGenere;
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getFavGenere() {
        return favGenere;
    }

    public void setFavGenere(String favGenere) {
        this.favGenere = favGenere;
    }


}
