package com.supensour.reactor.context;

import com.supensour.model.annotation.Experimental;

/**
 * @author Suprayan Yapura
 * @since 0.1.0
 */
@Experimental
public interface ReactorContextConfiguration {

  void registerContextSetting(ReactorContextSettingRegistry registry);

}
