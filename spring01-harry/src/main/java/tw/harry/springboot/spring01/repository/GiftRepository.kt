package tw.harry.springboot.spring01.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import tw.harry.springboot.spring01.entity.Gift

@Repository
interface GiftRepository : JpaRepository<Gift, Long> {


}