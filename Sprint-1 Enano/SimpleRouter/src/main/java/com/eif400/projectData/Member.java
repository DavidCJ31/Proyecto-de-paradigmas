
package com.eif400.projectData;

import java.io.Serializable;

/*
    Carlos Zhou Zheng
    Jose Martinez Sarmiento
    David Morales Hidalgo
    Manuel Guzman Rodriguez
    David Cordero Jimenez
 */
public class Member  implements Serializable{

    private String name, surname, id;

       public Member(String name, String surname, String id) {
        this.name = name;
        this.surname = surname;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }



}
