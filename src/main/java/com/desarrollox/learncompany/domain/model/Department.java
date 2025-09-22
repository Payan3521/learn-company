package com.desarrollox.learncompany.domain.model;

public class Department {
    private Long id;
    private String name;
    private String prize;
    private int hierarchy;

    public Department() {
    }

    public Department(Long id, String name, String prize, int hierarchy) {
        this.id = id;
        this.name = name;
        this.prize = prize;
        this.hierarchy = hierarchy;
    }

    public Department(String name, String prize, int hierarchy) {
        this.name = name;
        this.prize = prize;
        this.hierarchy = hierarchy;
    }
    
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getPrize() { return prize; }
    public void setPrize(String prize) { this.prize = prize; }

    public int getHierarchy() { return hierarchy; }
    public void setHierarchy(int hierarchy) { this.hierarchy = hierarchy; }

}