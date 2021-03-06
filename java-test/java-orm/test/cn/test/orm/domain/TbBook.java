package cn.test.orm.domain;

import java.util.Date;

public class TbBook {


    // Fields    

     private Integer bookId;
     private String bookName;
     private Float bookPrice;
     private String bookAuthor;
     private Date publishDate;
     private Float bookAmount;
     private BookType bookType;


    // Constructors

    /** default constructor */
    public TbBook() {
    }


   
    // Property accessors

    public Integer getBookId() {
        return this.bookId;
    }
    
    public void setBookId(Integer bookId) {
        this.bookId = bookId;
    }

    public String getBookName() {
        return this.bookName;
    }
    
    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public Float getBookPrice() {
        return this.bookPrice;
    }
    
    public void setBookPrice(Float bookPrice) {
        this.bookPrice = bookPrice;
    }

    public String getBookAuthor() {
        return this.bookAuthor;
    }
    
    public void setBookAuthor(String bookAuthor) {
        this.bookAuthor = bookAuthor;
    }

    public Date getPublishDate() {
        return this.publishDate;
    }
    
    public void setPublishDate(Date publishDate) {
        this.publishDate = publishDate;
    }

    public Float getBookAmount() {
        return this.bookAmount;
    }
    
    public void setBookAmount(Float bookAmount) {
        this.bookAmount = bookAmount;
    }

	public BookType getBookType() {
		return bookType;
	}
	public void setBookType(BookType bookType) {
		this.bookType = bookType;
	}
}	