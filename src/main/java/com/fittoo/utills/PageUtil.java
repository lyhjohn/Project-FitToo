package com.fittoo.utills;

import com.fittoo.page.model.TrainerPageParam;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;

public class PageUtil {


	public static int getCurPageAndAttributePageList(TrainerPageParam page, Model model,
		Long totalCount) {
		List<Integer> list = new ArrayList<>();
		int curPage = page.getCurPage();
		int curMaxPage = page.getCurMaxPage();
		int curMinPage = page.getCurMinPage();

		/**
		 * 이전페이지 or 다음페이지 이동
		 */
		if (StringUtils.hasText(page.getMovePage())) {
			if (page.getMovePage().equals("prev")) {
				if (curMinPage < 6) {
					for (int i = curMinPage; i <= curMaxPage; i++) {
						list.add(i);
					}
					model.addAttribute("curPage", list.get(0));
					model.addAttribute("pages", list);
					return list.get(0);
				} else {
					for (int i = curMinPage - 5; i <= curMinPage - 1; i++) {
						list.add(i);
					}
				}
				model.addAttribute("curPage", list.get(4));
				model.addAttribute("pages", list);
				return list.get(4);
			}

			if (page.getMovePage().equals("next")) {
				if (totalCount <= curMaxPage * 5L) {
					for (int i = curMinPage; i <= curMaxPage; i++) {
						list.add(i);
					}
				} else {
					for (int i = curMaxPage + 1; i <= curMaxPage + 5; i++) {
						list.add(i);
					}
				}
				model.addAttribute("curPage", list.get(0));
				model.addAttribute("pages", list);
				return list.get(0);
			}
		}

		/**
		 * 페이지 선택 X (초기 화면)
		 */
		if (curPage == 0) {
			list = Arrays.asList(1, 2, 3, 4, 5);
			model.addAttribute("curPage", list.get(0));
			model.addAttribute("pages", list);
			return list.get(0);
		}

		/**
		 * 특정 페이지 선택
		 */
		for (int i = curMinPage; i <= curMaxPage; i++) {
			list.add(i);
		}
		model.addAttribute("curPage", curPage);
		model.addAttribute("pages", list);
		return curPage;
	}
}
