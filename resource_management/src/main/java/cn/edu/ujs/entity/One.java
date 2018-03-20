package cn.edu.ujs.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.List;

/**
 * Created by DELL on 2018/3/16.
 */
@Getter
@Setter
public class One {


    private Integer id;

    private List<String> list;
}
