package com.jobportal.repository;

import com.jobportal.entity.IRecruiterJobs;
import com.jobportal.entity.JobPostActivity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface JobPostActivityRepository extends JpaRepository<JobPostActivity, Long> {

    @Query(value = " SELECT COUNT(s.user_id) as totalCandidates, j.job_post_id, j.job_title, l.id as locationId, l.city, l.state, l.country, c.id as companyId, c.name FROM job_post_activity j " +
            " INNER JOIN job_location l ON j.job_location_id = l.id " +
            " INNER JOIN job_company c ON j.job_company_id = c.id " +
            " LEFT JOIN job_seeker_apply s ON s.job = j.job_post_id " +
            " WHERE j.posted_by_id = :recruiter " +
            " GROUP BY j.job_post_id, j.job_title, l.id, l.city, l.state, l.country, c.id, c.name", nativeQuery = true)
    List<IRecruiterJobs> getRecruiterJobs(@Param("recruiter") Long recruiter);


    @Query(value = "SELECT * FROM job_post_activity j INNER JOIN job_location l ON j.job_location_id = l.id WHERE "
            + "(:job IS NULL OR j.job_title LIKE CONCAT('%', :job, '%')) "
            + "AND ((:location IS NULL) OR (l.city LIKE CONCAT('%', :location, '%') "
            + "OR l.country LIKE CONCAT('%', :location, '%') "
            + "OR l.state LIKE CONCAT('%', :location, '%'))) "
            + "AND (j.job_type IN (:type)) "
            + "AND ((:remote IS NULL) OR (j.remote IN (:remote)))", nativeQuery = true)
    List<JobPostActivity> searchWithoutDate(@Param("job") String job,
                                            @Param("location") String location,
                                            @Param("type") List<String> type,
                                            @Param("remote") List<String> remote);


    @Query(value = "SELECT * FROM job_post_activity j INNER JOIN job_location l on j.job_location_id=l.id  WHERE j" +
            ".job_title LIKE %:job%"
            + " AND (l.city LIKE %:location%"
            + " OR l.country LIKE %:location%"
            + " OR l.state LIKE %:location%) " +
            " AND (j.job_type IN(:type)) " +
            " AND (j.remote IN(:remote)) " +
            " AND (posted_date >= :date)", nativeQuery = true)
    List<JobPostActivity> search(@Param("job") String job,
                                 @Param("location" )String location,
                                 @Param("type")List<String> type,
                                 @Param("remote") List<String> remote,
                                 @Param("date") LocalDate searchDate);
}
