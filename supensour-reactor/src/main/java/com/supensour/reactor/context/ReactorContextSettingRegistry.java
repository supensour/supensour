package com.supensour.reactor.context;

import com.supensour.model.annotation.Experimental;
import com.supensour.model.common.DataRegistry;
import org.springframework.util.Assert;

/**
 * @author Suprayan Yapura
 * @since 0.1.0
 */
@Experimental
public class ReactorContextSettingRegistry extends DataRegistry<ReactorContextSetting<Object>> {

  public void register(ReactorContextSetting<?> setting) {
    Assert.notNull(setting, "Reactor context setting is null");
    Assert.notNull(setting.getKey(), "Context-aware thread setting key is null");
    Assert.isTrue(keyIsNotUsed(setting.getKey()), "Context-aware thread setting key has been used");
    //noinspection unchecked
    super.register((ReactorContextSetting<Object>) setting);
  }

  private boolean keyIsNotUsed(Object key) {
    return dataList.stream()
        .map(ReactorContextSetting::getKey)
        .noneMatch(key::equals);
  }

  public static ReactorContextSettingRegistry create() {
    return new ReactorContextSettingRegistry();
  }

}
