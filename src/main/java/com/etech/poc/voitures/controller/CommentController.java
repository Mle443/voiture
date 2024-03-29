package com.etech.poc.voitures.controller;

import com.etech.poc.voitures.data.dto.comment.CommentDTO;
import com.etech.poc.voitures.data.dto.comment.VehicleCommentDTO;
import com.etech.poc.voitures.services.applicatif.comment.CommentSA;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author benja
 */
@RestController
@RequestMapping(value = "/comment")
public class CommentController {

	@Autowired
	CommentSA commentSA;

	@PreAuthorize("hasRole('ROLE_USER')")
	@ApiOperation(value = "Save comment", notes = "Save the comment from an user to a vehicle")
	@PostMapping("/save")
	public ResponseEntity<CommentDTO> saveVehicle(@RequestBody CommentDTO dto) throws Exception {
		return new ResponseEntity<>(commentSA.saveComment(dto), HttpStatus.OK);
	}

	@PreAuthorize("hasRole('ROLE_USER')")
	@ApiOperation(value = "Get comments", notes = "Get comments for a vehicle")
	@GetMapping("/list")
	public ResponseEntity<VehicleCommentDTO> getVehicleComments(
			@ApiParam(name = "vehicleID") @RequestParam(defaultValue = "0") Long vehicleID,
			@ApiParam(name = "pageNo") @RequestParam(defaultValue = "0") Integer pageNo,
			@ApiParam(name = "pageSize") @RequestParam(defaultValue = "10") Integer pageSize) throws Exception {
		return new ResponseEntity<>(commentSA.getVehicleComment(vehicleID, pageNo, pageSize, null), HttpStatus.OK);
	}

}
