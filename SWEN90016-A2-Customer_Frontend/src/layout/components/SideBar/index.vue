<template>
  <div class="side-bar">
    <el-scrollbar wrap-class="scrollbar-wrapper">
      <el-menu
        :background-color="variable.menuBg"
        :text-color="variable.menuText"
        :unique-opened="false"
        :active-text-color="variable.menuActiveText"
        :collapse-transition="false"
        :router=true
        :default-active="$route.path"
        mode="vertical">
        <el-submenu index="1">
          <template slot="title">
            <i class="el-icon-s-promotion"></i>
            <span>Service</span>
          </template>
          <el-menu-item index="/project/service/introduceService">Introduce Service</el-menu-item>
          <el-menu-item index="/project/service/bookService" v-if="isLogin">Book Service</el-menu-item>
        </el-submenu>
        <el-submenu index="2" v-if="isLogin">
          <template slot="title">
            <i class="el-icon-s-promotion"></i>
            <span>Application</span>
          </template>
          <el-menu-item index="/project/appointment/pendingApplication">Pending Applications</el-menu-item>
          <el-menu-item index="/project/appointment/appointment">All Applications</el-menu-item>
        </el-submenu>
      </el-menu>
    </el-scrollbar>
  </div>
</template>

<script>
  import variable from '@/common/scss/variable.scss'
  import { mapGetters } from 'vuex'
  import store from '@/store'
  export default {
    name: 'sideBar',
    data () {
      return {
          isLogin: store.getters.isLogin
      }
    },
    computed: {
      variable () {
        return variable
      },
      ...mapGetters([
        'isSuper'
      ])
    }
  }
</script>

<style lang="scss">
  .side-bar {
    height: 100%;
  }
</style>
