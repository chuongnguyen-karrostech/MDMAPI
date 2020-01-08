package com.MDMREST.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.MDMREST.entity.mdm.loginWeb;
import com.MDMREST.service.mdm.LoginService;
import com.MDMREST.util.message.ActionReturn;
import com.MDMREST.util.message.CommonMessage;

@RestController
@CrossOrigin(origins = "*")
public class LoginController {

	@Autowired
	LoginService loginService;

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	@ResponseBody
	public ActionReturn login(@RequestBody loginWeb obj) {
		
		ActionReturn actreturn = new ActionReturn(null);
		Boolean loginWeb = loginService.loginWeb(obj.getUserName(), obj.getUserPass());
		if(loginWeb == true) 
		{
			actreturn = ActionReturn.Response(CommonMessage.True);
		}else 
		{
			actreturn = ActionReturn.Response(CommonMessage.False);
		}
		
		return actreturn;
	}
}
