package com.example.jpamysqlbasic;

import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookDTO {
    private Long id;
    private String title;
    private String author;
    private String isbn;
    private Double price;
    private Integer stock;

    // Entity를 DTO로 변환하는 정적 메서드
    public static BookDTO fromEntity(Book book) {
        return BookDTO.builder()
                .id(book.getId())
                .title(book.getTitle())
                .author(book.getAuthor())
                .isbn(book.getIsbn())
                .price(book.getPrice())
                .stock(book.getStock())
                .build();
    }

    // DTO를 Entity로 변환하는 메서드
    public Book toEntity() {
        return new Book(
                this.id,
                this.title,
                this.author,
                this.isbn,
                this.price,
                this.stock
        );
    }
}