package tw.harry.springboot.spring02.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import tw.harry.springboot.spring02.entity.Info

@Repository
interface InfoRepo : JpaRepository<Info, Long>