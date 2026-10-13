package com.hcl.library.service;

import com.*;
import java.util.*;

public class LibraryService {
    private final Map<String, Book> books = new LinkedHashMap<>();
    private final Map<String, Member> members = new LinkedHashMap<>();
    private final Map<String, Set<String>> issuedBooks = new HashMap<>();

    public void addBook(Book book) throws LibraryException {
        books.put(book.getBookId(), book);
    }

    public void registerMember(Member member) throws LibraryException {
        members.put(member.getMemberId(), member);
    }

    public void issueBook(String memberId, String bookId) throws LibraryException {
        Member member = members.get(memberId);
        Book book = books.get(bookId);
        Set<String> memberBooks = issuedBooks.computeIfAbsent(memberId, key -> new TreeSet<>());
        
        book.issueCopy();
        memberBooks.add(bookId);
    }

    public void returnBook(String memberId, String bookId) throws LibraryException {
        Member member = members.get(memberId);
        Book book = books.get(bookId);
        Set<String> memberBooks = issuedBooks.get(memberId);

        memberBooks.remove(bookId);
        book.returnCopy();
    }

    public List<Book> searchBookByTitle(String title) {
       List<Book> result = new ArrayList<>();
        for (Book book : books.values()) {
            if (book.getTitle().equalsIgnoreCase(title.trim())) {
                result.add(book);
            }
        }
        result.sort(Comparator.comparing(Book::getTitle));
        return result;
    }

    public List<Book> searchBookByAuthor(String author) {
        List<Book> result = new ArrayList<>();
        for (Book book : books.values()) {
            if (book.getAuthor().equalsIgnoreCase(author.trim())) {
                result.add(book);
            }
        }
        result.sort(Comparator.comparing(Book::getTitle));
        return result;
    }

    public List<Book> listBooks() {
        List<Book> result = new ArrayList<>(books.values());
        result.sort(Comparator.comparing(Book::getTitle));
        return result;
    }

    public List<Member> listMembers() {
        List<Member> result = new ArrayList<>(members.values());
        result.sort(Comparator.comparing(Member::getName));
        return result;
    }
}
