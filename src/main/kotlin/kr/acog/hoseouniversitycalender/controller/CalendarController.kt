package kr.acog.hoseouniversitycalender.controller

import kr.acog.hoseouniversitycalender.service.CalendarService
import org.springframework.core.io.FileSystemResource
import org.springframework.http.HttpHeaders
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import java.time.Instant

/**
 * Create by. Acog
 */
@RestController
class CalendarController(
    private val calendarService: CalendarService
) {

    @GetMapping("/hoseo-university-calendar.ics")
    fun getCalendar(): ResponseEntity<FileSystemResource> {
        val file = calendarService.getCalendarFile()
        val headers = HttpHeaders().apply {
            add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=${file.name}")
            add(HttpHeaders.CACHE_CONTROL, "public, max-age=0, must-revalidate")
            add(HttpHeaders.ETAG, file.lastModified().toString())
            add(HttpHeaders.LAST_MODIFIED, Instant.ofEpochMilli(file.lastModified()).toString())
        }
        return ResponseEntity.ok()
            .headers(headers)
            .body(FileSystemResource(file))
    }

    @GetMapping("/update")
    fun updateCalendar(): String {
        calendarService.updateCalendar()
        return "success"
    }
}