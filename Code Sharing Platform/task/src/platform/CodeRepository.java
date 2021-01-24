package platform;

import org.aspectj.apache.bcel.classfile.Code;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Repository
public interface CodeRepository extends CrudRepository<CodeSnippet, Integer> {
  ArrayList<CodeSnippet> findTop10BySecretFalseOrderByDateTimeDesc();

  Optional<CodeSnippet> findById(UUID uuid);
  // select top 10 *  from (select * from CODE_SNIPPET order by Date_Time desc)
//  @Query("SELECT TOP 10 u FROM CodeSnippet u")
//  List<CodeSnippet> get10LatestSnippets();
}
