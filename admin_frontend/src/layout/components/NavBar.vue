<template>
  <div class="navbar">
    <div class="breadcrumb-wrapper">
      <el-breadcrumb separator="/">
        <el-breadcrumb-item v-for="(item, index) in levelList" :key="index">
          <span class="isBack" v-if="item.backPath" @click="goBack(item.backPath)">{{item.meta.title}}</span>
          <span v-else>{{item.meta.title}}</span>
        </el-breadcrumb-item>
      </el-breadcrumb>
    </div>
    <div class="right-menu">
      <el-dropdown trigger="click">
        <div class="user-wrapper">
          <img class="avatar"
               src="https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif?imageView2/1/w/80/h/80"/>
          <span class="name">{{userName}}</span>
        </div>
        <el-dropdown-menu slot="dropdown">
          <el-dropdown-item @click.native="showChange">修改密码</el-dropdown-item>
          <el-dropdown-item @click.native="handleLogout">退出登录</el-dropdown-item>
        </el-dropdown-menu>
      </el-dropdown>
      <div class="dialog-wrapper">
        <el-dialog
          title="密码修改"
          :visible.sync="dialogShow"
          :close-on-click-modal=false
          @close="dialogClosed"
          width="600px">
          <el-form :model="formData" :rules="rules" ref="ruleForm" label-width="100px">
            <el-form-item label="旧密码" prop="oldPassword">
              <el-input placeholder="请输入旧密码" type="password" v-model="formData.oldPassword"></el-input>
            </el-form-item>
            <el-form-item label="新密码" prop="password">
              <el-input placeholder="请输入新密码" type="password" v-model="formData.password"></el-input>
            </el-form-item>
            <el-form-item label="确认密码" prop="checkPassword">
              <el-input placeholder="请确认密码" type="password" v-model="formData.checkPassword"></el-input>
            </el-form-item>
            <el-form-item>
              <el-button type="primary" @click="changePassword">保存</el-button>
              <el-button @click="toggleDialog">取消</el-button>
            </el-form-item>
          </el-form>
        </el-dialog>
      </div>
    </div>
  </div>
</template>

<script>
  import { mapGetters } from 'vuex'
  import { changePassword } from 'api/user'

  export default {
    name: 'navBar',
    data () {
      const validatePass2 = (rule, value, callback) => {
        if (value !== this.formData.password) {
          callback(new Error('两次密码输入不一致'))
        } else {
          callback()
        }
      }
      return {
        dialogShow: false,
        levelList: [],
        formData: {
          oldPassword: '',
          password: '',
          checkPassword: ''
        },
        rules: {
          oldPassword: [
            { required: true, message: '请输入旧密码', trigger: 'blur' },
            { min: 6, max: 12, message: '长度在6 到 12个字符', trigger: 'blur' }
          ],
          password: [
            { required: true, message: '请输入新密码', trigger: 'blur' },
            { min: 6, max: 12, message: '长度在6 到 12个字符', trigger: 'blur' }
          ],
          checkPassword: [
            { required: true, message: '请确认密码', trigger: 'blur' },
            { min: 6, max: 12, message: '长度在6 到 12个字符', trigger: 'blur' },
            { validator: validatePass2, trigger: 'blur' }
          ]
        }
      }
    },
    created () {
      this.getBreadcrumb()
    },
    methods: {
      handleLogout () {
        this.$store.dispatch('logout').then(() => {
          this.$router.push('/login')
        })
      },
      goBack (path) {
        this.$router.push(path)
      },
      getBreadcrumb () {
        const matched = this.$route.matched
        let levelList = matched.filter(item => item.meta && item.meta.title)
        let projectId = this.$route.params.projectId
        this.levelList = levelList.map(item => {
          if (item.meta.isBack) {
            let path = item.redirect || item.path
            item.backPath = projectId ? path.replace(':projectId', projectId) : path
          } else {
            item.backPath = ''
          }
          return item
        })
      },
      showChange () {
        this.toggleDialog()
      },
      toggleDialog () {
        this.dialogShow = !this.dialogShow
      },
      dialogClosed () {
        this.$refs.ruleForm.resetFields()
      },
      changePassword () {
        this.$refs.ruleForm.validate((valid) => {
          if (valid) {
            changePassword(this.formData).then(res => {
              if (res.success) {
                this.$message.success('密码修改成功')
                this.toggleDialog()
              }
            })
          } else {
            return false
          }
        })
      }
    },
    computed: {
      ...mapGetters([
        'userName'
      ])
    },
    watch: {
      $route () {
        this.getBreadcrumb()
      }
    }
  }
</script>

<style lang="scss" scoped>
  .navbar {
    position: relative;
    display: flex;
    padding: 0 20px;
    height: 60px;
    overflow: hidden;
    background: #fff;
    box-shadow: 0 1px 4px rgba(0, 21, 41, .08);
    align-items: center;
    justify-content: space-between;

    .breadcrumb-wrapper {
      .isBack {
        color: #409eff;
        cursor: pointer;
      }
    }

    .right-menu {
      cursor: pointer;

      .user-wrapper {
        display: flex;
        align-items: center;

        .avatar {
          width: 40px;
          height: 40px;
          margin-right: 10px;
          border-radius: 10px;
        }
      }
    }
  }
</style>
