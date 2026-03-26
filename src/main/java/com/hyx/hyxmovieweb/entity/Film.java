package com.hyx.hyxmovieweb.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "t_film")
public class Film {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "classify")
    private String classify;

    @Column(name = "director")
    private String director;

    @Column(name = "hero")
    private String hero;

    @Transient
    private String heroine;

    public String getHeroine() {
        return heroine;
    }

    public void setHeroine(String heroine) {
        this.heroine = heroine;
    }

    public String getProduction() {
        return production;
    }

    public void setProduction(String production) {
        this.production = production;
    }

    public String getOutline() {
        return outline;
    }

    public void setOutline(String outline) {
        this.outline = outline;
    }

    @Transient
    private String production;

    @Transient
    private String outline;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getClassify() {
        return classify;
    }

    public void setClassify(String classify) {
        this.classify = classify;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getHero() {
        return hero;
    }

    public void setHero(String hero) {
        this.hero = hero;
    }
}