package com.multicamp.controller;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.inject.Inject;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.multicamp.domain.PagingVO;
import com.multicamp.domain.PostVO;
import com.multicamp.service.PostService;

@RequestMapping("/api")
@RestController
public class PostReactController {
	@Inject
	private PostService postService;
	
	@PostMapping(value="/postAdd", produces="application/json")
	public ModelMap postCreate(@ModelAttribute PostVO vo,HttpSession ses) {
		ServletContext ctx=ses.getServletContext();
		String upDir=ctx.getRealPath("/upload");
		System.out.println("vo=="+vo+", upDir: "+upDir+"mfilename: "+vo.getMfilename());
		MultipartFile mfile=vo.getMfilename();
		if(mfile!=null&&!mfile.isEmpty()) {
			try {
				mfile.transferTo(new File(upDir,mfile.getOriginalFilename()));
			} catch (IllegalStateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		vo.setFilename(mfile.getOriginalFilename());
		int n=postService.createPost(vo);
		ModelMap map=new ModelMap();
		String str=(n>0)?"ok":"fail";
		map.put("result", str);
		return map;
	}//---------------------
	
	@GetMapping(value="/postList", produces="application/json")
	public List<PostVO> getPostList(@ModelAttribute PagingVO pvo){
		List<PostVO> postList=this.postService.listPosts(pvo);
		return postList;
	}
	
	@PostMapping(value="/postEdit",produces="application/json")
	public ModelMap updatePost(@ModelAttribute PostVO vo,HttpSession ses) {
		int n=this.postService.updatePost(vo);
		ModelMap map=new ModelMap();
		String str=(n>0)?"ok":"fail";
		map.put("result", str);
		return map;
	}

} 
