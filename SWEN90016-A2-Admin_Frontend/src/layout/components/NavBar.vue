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
          <span class="name">{{userInfo.name}}</span>
        </div>
        <el-dropdown-menu slot="dropdown">
          <el-dropdown-item @click.native="showChange">Change Password</el-dropdown-item>
          <el-dropdown-item @click.native="showChangeDetail">Change Detail</el-dropdown-item>
          <el-dropdown-item @click.native="handleLogout">Logout</el-dropdown-item>
        </el-dropdown-menu>
      </el-dropdown>
      <div class="dialog-wrapper">
        <el-dialog
          title="Edit Password"
          :visible.sync="dialogShow"
          :close-on-click-modal=false
          @close="dialogClosed"
          width="600px">
          <el-form :model="formData" :rules="rules" ref="ruleForm" label-width="100px">
            <el-form-item label="Original " prop="oldPassword">
              <el-input placeholder="Please input the original password" type="password" v-model="formData.oldPassword"></el-input>
            </el-form-item>
            <el-form-item label="New " prop="password">
              <el-input placeholder="Please input new password" type="password" v-model="formData.password"></el-input>
            </el-form-item>
            <el-form-item label="Confirm " prop="checkPassword">
              <el-input placeholder="PLease confirm password" type="password" v-model="formData.checkPassword"></el-input>
            </el-form-item>
            <el-form-item>
              <el-button type="primary" @click="changePassword" :loading="loading">Save</el-button>
              <el-button @click="toggleDialog" :loading="loading">Cancel</el-button>
            </el-form-item>
          </el-form>
        </el-dialog>
        <el-dialog
          title="Edit Detail"
          :visible.sync="dialogShowDetail"
          :close-on-click-modal=false
          width="600px"
          @closed="dialogClosed">
          <el-form :model="userInfo" label-width="100px">
            <el-form-item label="name">
              <el-input placeholder="name" v-model="userInfo.name"></el-input>
            </el-form-item>
            <el-form-item label="email">
              <el-input placeholder="email" v-model="userInfo.email"></el-input>
            </el-form-item>
            <el-form-item>
              <el-button type="primary" @click="save">Save</el-button>
              <el-button @click="toggleDialogDetail">Cancel</el-button>
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
  import { getAdmin, updateAdmin } from 'api/adminAPI'
  import store from '@/store'

  export default {
    name: 'navBar',
    data () {
      const validatePass2 = (rule, value, callback) => {
        if (value !== this.formData.password) {
          callback(new Error('password does not match'))
        } else {
          callback()
        }
      }
      return {
        loading: false,
        dialogShow: false,
        dialogShowDetail: false,
        levelList: [],
        userInfo: {
            id: store.getters.userId,
            name: '',
            email: '',
            password: ''
        },
        formData: {
          oldPassword: '',
          password: '',
          checkPassword: ''
        },
          formDataDetail: {
              name: '',
              email: ''
          },
        rules: {
          oldPassword: [
            { required: true, message: 'empty', trigger: 'blur' },
            { min: 6, max: 12, message: 'must be 6 - 12 character', trigger: 'blur' }
          ],
          password: [
            { required: true, message: 'empty', trigger: 'blur' },
            { min: 6, max: 12, message: 'must be 6 - 12 character', trigger: 'blur' }
          ],
          checkPassword: [
            { required: true, message: 'empty', trigger: 'blur' },
            { min: 6, max: 12, message: 'must be 6 - 12 character', trigger: 'blur' },
            { validator: validatePass2, trigger: 'blur' }
          ]
        }
      }
    },
    created () {
      this.getBreadcrumb()
      this.getUser()
    },
    methods: {
        async save () {
            let message = ''
            let res = null
            res = await updateAdmin(this.userInfo)
            message = 'Edit Successful'
            if (res.success) {
                this.$message.success(message)
                this.getUser()
                this.toggleDialogDetail()
            } else {
                this.getUser()
            }
        },
      async getUser () {
        const res = await getAdmin(store.getters.userId)
        if (res.success) {
            this.userInfo = res.data
        } else {
            this.getUser()
        }
      },
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
        showChangeDetail () {
            this.toggleDialogDetail()
        },
        toggleDialogDetail () {
            this.dialogShowDetail = !this.dialogShowDetail
        },
      dialogClosed () {
        this.$refs.ruleForm.resetFields()
      },
      changePassword () {
        this.$refs.ruleForm.validate((valid) => {
          if (valid) {
            this.loading = true
            changePassword(this.formData).then(res => {
              if (res.success) {
                this.$message.success('密码修改成功')
                this.toggleDialog()
                this.getUser()
              }
            })
            this.loading = false
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
