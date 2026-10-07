package tw.harry.springboot.spring04.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import tw.harry.springboot.spring04.entity.Gift

@Repository
interface GiftRepo : JpaRepository<Gift, Long>