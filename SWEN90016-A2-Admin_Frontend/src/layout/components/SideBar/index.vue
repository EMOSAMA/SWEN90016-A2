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
            <span>Appointment</span>
          </template>
          <el-menu-item index="/project/appointment/todayAppointment">Today Appointment</el-menu-item>
          <el-menu-item index="/project/appointment/allAppointment">All Appointment</el-menu-item>
        </el-submenu>
        <el-submenu index="2">
          <template slot="title">
            <i class="el-icon-s-promotion"></i>
            <span>User</span>
          </template>
          <el-menu-item index="/project/user/customer">Customer</el-menu-item>
        </el-submenu>
        <el-submenu index="3">
          <template slot="title">
            <i class="el-icon-s-promotion"></i>
            <span>Service</span>
          </template>
          <el-menu-item index="/project/service/service">Service</el-menu-item>
        </el-submenu>
      </el-menu>
    </el-scrollbar>
  </div>
</template>

<script>
  import variable from '@/common/scss/variable.scss'
  import { mapGetters } from 'vuex'

  export default {
    name: 'sideBar',
    data () {
      return {}
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
