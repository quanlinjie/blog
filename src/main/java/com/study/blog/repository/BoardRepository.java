package com.study.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.study.blog.model.Board;

public interface BoardRepository extends JpaRepository<Board, Integer>{

}