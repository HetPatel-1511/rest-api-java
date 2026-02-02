package com.example.restapi.mapper;

import com.example.restapi.dto.request.CreateEmployeeDTO;
import com.example.restapi.dto.response.AuthEmployeeResponseDTO;
import com.example.restapi.dto.response.EmployeeResponseDTO;
import com.example.restapi.entities.Employee;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class EntityMapper {

    @Autowired
    private ModelMapper modelMapper;

    public Employee toEmployee(CreateEmployeeDTO employee) {
        if (employee == null) return null;
        return modelMapper.map(employee, Employee.class);
    }

    public EmployeeResponseDTO toEmployeeResponseDTO(Employee employee) {
        if (employee == null) return null;
        return modelMapper.map(employee, EmployeeResponseDTO.class);
    }

    public AuthEmployeeResponseDTO toAuthEmployeeResponseDTO(Employee employee) {
        if (employee == null) return null;
        return modelMapper.map(employee, AuthEmployeeResponseDTO.class);
    }

    public List<EmployeeResponseDTO> toEmployeeResponseDTOList(List<Employee> list) {
        if (list == null) return Collections.emptyList();
        return list.stream().map(this::toEmployeeResponseDTO).collect(Collectors.toList());
    }
//
//    public Library toLibrary(LibraryDTO dto) {
//        if (dto == null) return null;
//        return modelMapper.map(dto, Library.class);
//    }
//
//    public List<LibraryDTO> toLibraryDTOList(List<Library> list) {
//        if (list == null) return Collections.emptyList();
//        return list.stream().map(this::toLibraryDTO).collect(Collectors.toList());
//    }
//
//    public BookDTO toBookDTO(Book book) {
//        if (book == null) return null;
//        BookDTO dto = modelMapper.map(book, BookDTO.class);
//        if (book.getLibrary() != null) dto.setLibraryId(book.getLibrary().getId());
//        if (book.getAuthors() != null) {
//            dto.setAuthorIds(book.getAuthors().stream().map(Author::getId).collect(Collectors.toList()));
//        }
//        return dto;
//    }
//
//    public List<BookDTO> toBookDTOList(List<Book> books) {
//        if (books == null) return Collections.emptyList();
//        return books.stream().map(this::toBookDTO).collect(Collectors.toList());
//    }
//
//    public AuthorDTO toAuthorDTO(Author author) {
//        if (author == null) return null;
//        return modelMapper.map(author, AuthorDTO.class);
//    }
//
//    public Author toAuthor(AuthorDTO dto) {
//        if (dto == null) return null;
//        return modelMapper.map(dto, Author.class);
//    }
//
//    public List<AuthorDTO> toAuthorDTOList(List<Author> list) {
//        if (list == null) return Collections.emptyList();
//        return list.stream().map(this::toAuthorDTO).collect(Collectors.toList());
//    }
}
