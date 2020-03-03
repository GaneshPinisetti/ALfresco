/**
 * 
 */
package com.alfresco.verinon.utility.export.dto;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

/**
 * @author goura
 *
 */
public interface FolderRepository extends CrudRepository<Folder, Long>{

	List<Folder> findByJobRefAndNodeType(String jobRef, String string);

}
