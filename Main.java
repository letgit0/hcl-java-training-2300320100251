package com.hcl.library;

import java.util.*;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        LibraryService library = new LibraryService();
        library.addBook(new Book("B101", "Java Fundamentals", "James Gosling", "Programming", 3));
        library.addBook(new Book("B102", "Clean Code", "Robert C. Martin", "Programming", 2));
        library.registerMember(new Member("M101", "Alice Johnson", "9876543210"));
        showMenu(library);
    }

    private static void showMenu(LibraryService library) {
        Scanner sc = new Scanner(System.in);

        while (true) {
            ConsoleUtil.printMenu();
            int choice = ConsoleUtil.readInt(sc, "Choose: ");

            try {
                switch (choice) {
                    case 1: {
                        String id = ConsoleUtil.readNonEmpty(sc, "Book ID: ");
                        String title = ConsoleUtil.readNonEmpty(sc, "Title: ");
                        String author = ConsoleUtil.readNonEmpty(sc, "Author: ");
                        String category = ConsoleUtil.readNonEmpty(sc, "Category: ");
                        int copies = ConsoleUtil.readInt(sc, "Copies: ");
                        library.addBook(new Book(id, title, author, category, copies));
                        System.out.println("Book added successfully.");
                        break;
                    }
                    case 2: {
                        String id = ConsoleUtil.readNonEmpty(sc, "Member ID: ");
                        String name = ConsoleUtil.readNonEmpty(sc, "Name: ");
                        String phone = ConsoleUtil.readNonEmpty(sc, "Phone: ");
                        library.registerMember(new Member(id, name, phone));
                        System.out.println("Member registered successfully.");
                        break;
                    }
                    case 3: {
                        String memberId = ConsoleUtil.readNonEmpty(sc, "Member ID: ");
                        String bookId = ConsoleUtil.readNonEmpty(sc, "Book ID: ");
                        library.issueBook(memberId, bookId);
                        System.out.println("Book issued successfully.");
                        break;
                    }
                    case 4: {
                        String memberId = ConsoleUtil.readNonEmpty(sc, "Member ID: ");
                        String bookId = ConsoleUtil.readNonEmpty(sc, "Book ID: ");
                        library.returnBook(memberId, bookId);
                        System.out.println("Book returned successfully.");
                        break;
                    }
                    case 5: {
                        String title = ConsoleUtil.readNonEmpty(sc, "Title: ");
                        List<Book> books = library.searchBookByTitle(title);
                        if (books.isEmpty()) System.out.println("No books found.");
                        else books.forEach(System.out::println);
                        break;
                    }
                    case 6: {
                        String author = ConsoleUtil.readNonEmpty(sc, "Author: ");
                        List<Book> books = library.searchBookByAuthor(author);
                        if (books.isEmpty()) System.out.println("No books found.");
                        else books.forEach(System.out::println);
                        break;
                    }
                    case 7:
                        library.listBooks().forEach(System.out::println);
                        break;
                    case 8:
                        library.listMembers().forEach(System.out::println);
                        break;
                    case 9:
                        System.out.println("Goodbye!");
                        sc.close();
                        return;
                    default:
                        System.out.println("Invalid option. Please choose between 1 and 9.");
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }
}

class ConsoleUtil {
    public static void printMenu() {
        System.out.println("\n--- Library Management System ---");
        System.out.println("1. Add Book");
        System.out.println("2. Register Member");
        System.out.println("3. Issue Book");
        System.out.println("4. Return Book");
        System.out.println("5. Search by Title");
        System.out.println("6. Search by Author");
        System.out.println("7. View All Books");
        System.out.println("8. View All Members");
        System.out.println("9. Exit");
    }

    public static String readNonEmpty(Scanner sc, String label) {
        while (true) {
            System.out.print(label);
            String value = sc.nextLine().trim();
            if (!value.isEmpty()) {
                return value;
            }
            System.out.println("This field cannot be empty.");
        }
    }

    public static int readInt(Scanner sc, String label) {
        while (true) {
            System.out.print(label);
            String value = sc.nextLine().trim();
            try {
                return Integer.parseInt(value);
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid integer.");
            }
        }
    }
}

class Book {
    private String id;
    private String title;
    private String author;
    private String category;
    private int copies;

    public Book(String id, String title, String author, String category, int copies) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.category = category;
        this.copies = copies;
    }

    public String getId() { return id; }
    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public int getCopies() { return copies; }
    public void setCopies(int copies) { this.copies = copies; }

    @Override
    public String toString() {
        return String.format("ID: %s | Title: %s | Author: %s | Category: %s | Copies: %d", 
                id, title, author, category, copies);
    }
}

class Member {
    private String id;
    private String name;
    private String phone;

    public Member(String id, String name, String phone) {
        this.id = id;
        this.name = name;
        this.phone = phone;
    }

    public String getId() { return id; }

    @Override
    public String toString() {
        return String.format("Member ID: %s | Name: %s | Phone: %s", id, name, phone);
    }
}

class LibraryService {
    private Map<String, Book> books = new HashMap<>();
    private Map<String, Member> members = new HashMap<>();

    public void addBook(Book book) {
        books.put(book.getId(), book);
    }

    public void registerMember(Member member) {
        members.put(member.getId(), member);
    }

    public void issueBook(String memberId, String bookId) throws Exception {
        if (!members.containsKey(memberId)) throw new Exception("Member not found.");
        Book book = books.get(bookId);
        if (book == null) throw new Exception("Book not found.");
        if (book.getCopies() <= 0) throw new Exception("No copies available.");
        
        book.setCopies(book.getCopies() - 1);
    }

    public void returnBook(String memberId, String bookId) throws Exception {
        if (!members.containsKey(memberId)) throw new Exception("Member not found.");
        Book book = books.get(bookId);
        if (book == null) throw new Exception("Book not found.");
        
        book.setCopies(book.getCopies() + 1);
    }

    public List<Book> searchBookByTitle(String title) {
        return books.values().stream()
                .filter(b -> b.getTitle().toLowerCase().contains(title.toLowerCase()))
                .collect(Collectors.toList());
    }

    public List<Book> searchBookByAuthor(String author) {
        return books.values().stream()
                .filter(b -> b.getAuthor().toLowerCase().contains(author.toLowerCase()))
                .collect(Collectors.toList());
    }

    public Collection<Book> listBooks() {
        return books.values();
    }

    public Collection<Member> listMembers() {
        return members.values();
    }
}