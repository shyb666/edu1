<script setup>

</script>


<template>
  <ul class="app-header-nav">
    <li class="home">
      <RouterLink to="/">首页</RouterLink>
    </li>
    <li class="home" style="width: auto;">
      <RouterLink to="/searchbytype/计算机">计算机</RouterLink>
    </li>
    <li class="home">
      <RouterLink to="/searchbytype/数学">数学</RouterLink>
    </li>
    <li class="home">
      <RouterLink to="/searchbytype/英语">英语</RouterLink>
    </li>
    <li class="home">
      <RouterLink to="/searchbytype/政治">政治</RouterLink>
    </li>
    <li class="home">
      <RouterLink to="/searchbytype/法律">法律</RouterLink>
    </li>
    <li class="home">
      <RouterLink to="/searchbytype/美术">美术</RouterLink>
    </li>
    <li class="home" style="width: auto;">
      <RouterLink to="/searchbytype/其他">其他</RouterLink>
    </li>
    
  </ul>
</template>


<style lang="scss">
.app-header-nav {
  width: 820px;
  display: flex;
  padding-left: 40px;
  position: relative;
  z-index: 998;

  li {
    margin-right: 40px;
    width: 38px;
    text-align: center;

    a {
      font-size: 16px;
      line-height: 32px;
      height: 32px;
      display: inline-block;

      &:hover {
        color: $xtxColor;
        border-bottom: 1px solid $xtxColor;
      }
    }

    .active {
      color: $xtxColor;
      border-bottom: 1px solid $xtxColor;
    }
  }
}
</style>