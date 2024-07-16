package com.project.shelf.book;


import com.project.shelf._core.erros.exception.Exception400;
import com.project.shelf._core.erros.exception.Exception401;
import com.project.shelf._core.util.MyFileUtil;
import com.project.shelf.admin.AdminRequestRecord.BookSaveReqDTO;
import com.project.shelf.author.Author;
import com.project.shelf.author.AuthorRepository;
import com.project.shelf.book.BookResponseRecord.BookCategorySearchDTO;
import com.project.shelf.book.BookResponseRecord.BookRankRespDTO;
import com.project.shelf.book.BookResponseRecord.BrandNewRespDTO;
import com.project.shelf.user.SessionUser;
import com.project.shelf.user.User;
import com.project.shelf.user.UserRepository;
import com.project.shelf.wishlist.Wishlist;
import com.project.shelf.wishlist.WishlistRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.TemporalAdjusters;
import java.time.temporal.WeekFields;
import java.util.*;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;


@RequiredArgsConstructor
@Service
@Slf4j
public class BookService {
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final WishlistRepository wishlistRepository;
    private final UserRepository userRepository;

    //전체 베스트셀러 랭킹
    public List<BookRankRespDTO> getBooksByCategory(String category) {
        List<Wishlist> wishlists;

        if (category == null || category.isEmpty() || category.equalsIgnoreCase("종합")) {
            // 전체 검색
            wishlists = wishlistRepository.findAllBooksInWishlist();
        } else {
            try {
                Book.Category enumCategory = Book.Category.valueOf(category);
                wishlists = wishlistRepository.findAllBooksInWishlist(enumCategory);
            } catch (IllegalArgumentException e) {
                wishlists = List.of();
            }
        }

        return wishlists.stream()
                .map(wishlist -> {
                    Book book = wishlist.getBook();
                    return new BookRankRespDTO(
                            book.getId(),
                            book.getTitle(),
                            book.getPath(),
                            new BookRankRespDTO.Author(
                                    book.getAuthor().getId(),
                                    book.getAuthor().getName()
                            )
                    );
                })
                .distinct() // 중복 제거
                .sorted((b1, b2) -> b1.bookId().compareTo(b2.bookId())) // 오름차순 정렬
                .collect(Collectors.toList());
    }


    public List<BrandNewRespDTO> brandNew(String registrationMonth) {
        // 전달 받은 해당 월(2024-06)에 출간된 책 검색
        String[] parts = registrationMonth.split("-");

        int year = Integer.parseInt(parts[0]);
        int month = Integer.parseInt(parts[1]);

        LocalDate startDate = LocalDate.of(year, month, 1);
        LocalDate endDate = startDate.withDayOfMonth(startDate.lengthOfMonth()); // 해당 월의 마지막날 구하기

        List<Book> books = bookRepository.findByRegistrationMonth(startDate.atStartOfDay(), endDate.atStartOfDay().with(LocalTime.MAX));

        // 검색한 list를 주별로 구분
        Map<Integer, List<Book>> weeklyData = splitDataByWeeks(books, startDate, endDate);

        // 주별 데이터 담기
        List<BrandNewRespDTO> brandNewRespDTOList = new ArrayList<>();
        for (Map.Entry<Integer, List<Book>> entry : weeklyData.entrySet()) {
            List<BrandNewRespDTO.brandNewList> list = entry.getValue().stream().map(book -> {
                return BrandNewRespDTO.brandNewList.builder()
                       .bookId(book.getId())
                       .title(book.getTitle())
                       .author(book.getAuthor().getName())
                       .path(book.getPath())
                       .build();
            }).toList();
            String weekName = weekIntegerToString(entry.getKey());


            brandNewRespDTOList.add(new BrandNewRespDTO(weekName, list));
        }

        return brandNewRespDTOList;
    }

    // 몇째 주인지 string으로 변환
    private static String weekIntegerToString(Integer weekNumber){
        String weekName = switch (weekNumber) {
            case 1 -> "첫째";
            case 2 -> "둘째";
            case 3 -> "셋째";
            case 4 -> "넷째";
            case 5 -> "다섯째";
            default -> "";
        };
        return weekName;
    }

    // 데이터를 주 단위로 쪼개는 메서드
    private static Map<Integer, List<Book>> splitDataByWeeks(List<Book> books, LocalDate startDate, LocalDate endDate) {
        Map<Integer, List<Book>> weeklyData = new HashMap<>();
        WeekFields weekFields = WeekFields.of(Locale.getDefault());
        LocalDate start = startDate.with(TemporalAdjusters.previousOrSame(weekFields.getFirstDayOfWeek()));
        LocalDate end = endDate.with(TemporalAdjusters.nextOrSame(weekFields.getFirstDayOfWeek()));

        int weekNumber = 1;
        while (start.isBefore(end)) {
            LocalDate weekEnd = start.plusDays(6);
            for (Book book : books) {
                if (!book.getRegistrationDate().isBefore(start.atStartOfDay()) && !book.getRegistrationDate().isAfter(weekEnd.atStartOfDay())) {
                    weeklyData.computeIfAbsent(weekNumber, k -> new ArrayList<>()).add(book);
                }
            }
            start = start.plusWeeks(1);
            weekNumber++;
        }

        return weeklyData;
    }


    //책 카테고리와 어서 검색 결과 페이지
    public BookCategorySearchDTO bookSearch(String category, String authorName) {
        List<Book> books;

        if (authorName != null && !authorName.isEmpty()) {
            Author author = authorRepository.findByName(authorName)
                    .orElseThrow(() -> new Exception400("저자를 찾을 수 없습니다."));
            books = bookRepository.findByAuthorId(author.getId());
            if (books.isEmpty()) {
                throw new Exception400("저자의 책을 찾을 수 없습니다.");
            }
        } else {
            try {
                Book.Category bookCategory = Book.Category.valueOf(category);
                books = bookRepository.findByCategory(bookCategory);
                if (books.isEmpty()) {
                    throw new Exception400("카테고리에 맞는 책 정보를 찾을 수 없습니다.");
                }
            } catch (IllegalArgumentException e) {
                throw new Exception400("유효하지 않은 카테고리입니다.");
            }
        }

        Integer bookCount = books.size();

        List<BookCategorySearchDTO.Book> bookDTOs = books.stream()
                .map(book -> BookCategorySearchDTO.Book.builder()
                        .bookId(book.getId())
                        .bookTitle(book.getTitle())
                        .bookPath(book.getPath())
                        .author(BookCategorySearchDTO.Book.Author.builder()
                                .authorId(book.getAuthor().getId())
                                .authorName(book.getAuthor().getName())
                                .build())
                        .build())
                .collect(Collectors.toList());

        return BookCategorySearchDTO.builder()
                .bookCount(bookCount)
                .book(bookDTOs)
                .build();
    }

    public void saveBook(BookSaveReqDTO reqDTO) throws IOException {
        // 저자 정보 가져오기 또는 생성하기
        Author author = authorRepository.findByName(reqDTO.author())
                .orElseGet(() -> {
                    Author newAuthor = new Author();
                    newAuthor.setName(reqDTO.author());
                    newAuthor.setAuthorIntro(reqDTO.authorIntro());
                    return authorRepository.save(newAuthor);
                });

        // 이미지 파일 저장
        String imagePath = MyFileUtil.write(reqDTO.path(), "/image/");

        // epub 파일 저장
        String epubFilePath = MyFileUtil.write(reqDTO.epubFile(), "/image/epub/");

        // Book 객체 생성 및 저장
        Book book = Book.builder()
                .author(author)
                .title(reqDTO.title())
                .path(imagePath)
                .pageCount(reqDTO.pageCount())
                .bookIntro(reqDTO.bookIntro())
                .contentIntro(reqDTO.contentIntro())
                .category(reqDTO.category())
                .publisher(reqDTO.publisher())
                .epubFile(epubFilePath)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        bookRepository.save(book);
    }

    // 책 상세보기 페이지
    public BookResponse.DetailPageDTO bookDetailPage(SessionUser sessionUser, Integer bookId) {
//        SessionUser sessionUser = AppJwtUtil.verify(jwt);
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new Exception401("책 정보를 찾을 수 없습니다!!"));
        // 사용자 가져오기
        User user = userRepository.findById(sessionUser.getId())
                .orElseThrow(() -> new Exception401("사용자를 찾을 수 없습니다!!"));
        // 저자 가져오기
        Author author = authorRepository.findById(book.getAuthor().getId())
                .orElseThrow(() -> new Exception401("저자를 찾을 수 없습니다!!"));
        book.setAuthor(author);

        // 위시리스트 여부
        Boolean isWish = wishlistRepository.existsByUserAndBook(user, book);

        return new BookResponse.DetailPageDTO(book, isWish);
    }
}
