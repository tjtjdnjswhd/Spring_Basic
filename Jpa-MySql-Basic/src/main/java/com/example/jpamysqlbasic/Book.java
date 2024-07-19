package com.example.jpamysqlbasic;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Table(name = "books")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    // id: 책의 고유 식별자. 데이터베이스에서 자동으로 생성됩니다.

    @Column(nullable = false)
    private String title;
    // title: 책의 제목. null이 될 수 없습니다.

    @Column(nullable = false)
    private String author;
    // author: 책의 저자. null이 될 수 없습니다.

    @Column(nullable = false)
    private String isbn;
    // isbn: 국제 표준 도서 번호. 각 책의 고유 식별 번호입니다. null이 될 수 없습니다.

    @Column(nullable = false)
    private Double price;
    // price: 책의 가격. null이 될 수 없습니다.

    @Column
    private Integer stock;
    // stock: 재고 수량. null이 허용됩니다 (재고가 없을 수 있음).
}