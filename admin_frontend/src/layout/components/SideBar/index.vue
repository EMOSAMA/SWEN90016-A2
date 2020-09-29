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
        <!--
        <el-submenu index="1">
          <template slot="title">
            <i class="el-icon-user-solid"></i>
            <span>项目管理</span>
          </template>
          <el-menu-item index="/project/user/data">账号管理</el-menu-item>
          <el-menu-item index="/project/task/data">任务管理</el-menu-item>
        </el-submenu>
        <el-submenu index="2" v-if="isSuper">
          <template slot="title">
            <i class="el-icon-s-promotion"></i>
            <span>设备管理</span>
          </template>
          <el-menu-item index="/device/position">定位设备</el-menu-item>
          <el-menu-item index="/device/traffic">交通参数分析服务器</el-menu-item>
          <el-menu-item index="/device/calculate">移动边缘计算服务器</el-menu-item>
        </el-submenu>
        -->
        <el-submenu index="1">
          <template slot="title">
            <i class="el-icon-s-promotion"></i>
            <span>账号管理</span>
          </template>
          <el-menu-item index="/project/user/student">学生账号</el-menu-item>
          <el-menu-item index="/project/user/teacher">教师账号</el-menu-item>
          <el-menu-item index="/project/user/admin" v-if="isSuper">管理员账号</el-menu-item>
        </el-submenu>
        <el-submenu index="2">
          <template slot="title">
            <i class="el-icon-s-promotion"></i>
            <span>课程管理</span>
          </template>
          <el-menu-item index="/project/unit/unit">课程管理</el-menu-item>
        </el-submenu>
        <el-submenu index="3">
          <template slot="title">
            <i class="el-icon-s-promotion"></i>
            <span>学校架构</span>
          </template>
          <el-menu-item index="/project/structure/department">学院</el-menu-item>
          <el-menu-item index="/project/structure/major">专业</el-menu-item>
          <el-menu-item index="/project/structure/class">班级</el-menu-item>
        </el-submenu>
        <el-submenu index="4">
          <template slot="title">
            <i class="el-icon-s-promotion"></i>
            <span>图书中心</span>
          </template>
          <!--<el-menu-item index="/project/library/books">书籍管理</el-menu-item>-->
          <el-menu-item index="/project/library/rentBooks">出借书籍</el-menu-item>
        </el-submenu>
        <el-submenu index="5">
          <template slot="title">
            <i class="el-icon-s-promotion"></i>
            <span>学校补助</span>
          </template>
          <el-menu-item index="/project/apply/apply">补助项目管理</el-menu-item>
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
