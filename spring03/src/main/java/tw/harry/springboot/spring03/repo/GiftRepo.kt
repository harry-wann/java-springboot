package tw.harry.springboot.spring03.repo

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import tw.harry.springboot.spring03.entity.Gift

@Repository
interface GiftRepo : JpaRepository<Gift, Long>