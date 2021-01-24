package platform;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;


@Repository
public interface CodeRepository extends CrudRepository<CodeSnippet, Integer> {
  ArrayList<CodeSnippet> findTop10ByOrderByDateTimeDesc();
  // select top 10 *  from (select * from CODE_SNIPPET order by Date_Time desc)
//  @Query("SELECT TOP 10 u FROM CodeSnippet u")
//  List<CodeSnippet> get10LatestSnippets();
}
