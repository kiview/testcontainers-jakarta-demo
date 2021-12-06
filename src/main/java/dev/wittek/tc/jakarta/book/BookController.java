package dev.wittek.tc.jakarta.book;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

import java.util.List;

@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Path("book")
public class BookController {

    @Inject
    private BookRepository bookRepository;

    @Path("list")
    @GET
    public List<Book> allBooks() {
        return bookRepository.findAll();
    }

    @Path("add")
    @POST
    public void addBook(Book book) {
        bookRepository.save(book);
    }

    @Path("find")
    @GET
    public List<Book> findBooksByAuthor(@QueryParam("author") String author) {
        return bookRepository.findByAuthor(author);
    }

}
