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
public interface FileNodeRepository extends CrudRepository<FileNode, Long>{

	List<FileNode> findByJobRefAndNodeTypeNot(String jobRef, String type);

}
